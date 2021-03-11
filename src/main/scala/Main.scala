import collection.JavaConverters._
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkContext
import org.apache.log4j.Logger
import org.apache.log4j.Level

import com.datarobot.spark.api.{CodegenModel, CodegenModelFactory}

import com.datarobot.mlops.MLOps;
import com.datarobot.mlops.common.enums.OutputType;
import com.datarobot.mlops.spark.MLOpsSparkUtils

import org.apache.spark.TaskContext
import scala.collection.JavaConversions._

import com.datarobot.spark.api.{CodegenModel, CodegenModelFactory}

import java.util

object Main extends App {

  Logger.getLogger("org").setLevel(Level.WARN)
  Logger.getLogger("akka").setLevel(Level.WARN)

  val numRecords = if (args.size < 1) 5 else args(0).toInt
  val conf = new SparkConf().setMaster("local[*]").setAppName("scoring-app-with-agents").set("spark.driver.maxResultSize", "2G")
  val sc = new SparkContext(conf) // initialize spark context
  val sqlContext = new org.apache.spark.sql.SQLContext(sc) // initialize sql context
  implicit val spark = SparkSession.builder.config(conf).getOrCreate() // start spark session
  import spark.implicits._
  // read in the lending club data
  val df = sqlContext.read.format("csv").option("header", "true").load("data/10K_Lending_Club_Loans.csv")
  // load the codegen model
  val codegenModel: CodegenModel = CodegenModelFactory.apply("5d5da72a3fa59e2850f824fc")
  // print out the context config
  sc.getConf.getAll.foreach(println)
  // create predictions

  val predictions = codegenModel.transform(df.limit(numRecords))
  val start = System.currentTimeMillis()
  predictions.count
  val stop = System.currentTimeMillis()

  val mlopsOutputType = sys.env("MLOPS_SPOOLER_TYPE")
  val mlopsModelId = sys.env.get("MLOPS_MODEL_ID")
  val mlopsDeploymentId = sys.env.get("MLOPS_DEPLOYMENT_ID")
  val mlopsOutputDir = sys.env.get("MLOPS_FILESYSTEM_DIRECTORY")

  val configString = mlopsOutputType match {
    case "STDOUT" => s"spooler_type=STDOUT"
    case "FILESYSTEM" =>
      s"spooler_type=FILESYSTEM,directory=${mlopsOutputDir}"
    case "OUTPUT_DIR" => s"spooler_type=OUTPUT_DIR,directory=${mlopsOutputDir.get}"
    case _ =>
      throw new Exception("only set to work with stdout, filesystem, or output_dir")
  }

  println(s"config string : ${configString}")

  val predictionColumns = List("target_1_PREDICTION", "target_0_PREDICTION")
  MLOpsSparkUtils.reportPredictions(
    predictions,
    sys.env("MLOPS_DEPLOYMENT_ID"),
    sys.env("MLOPS_MODEL_ID"),
    configString,
    (stop - start).toInt,
    predictionColumns
  )

}
