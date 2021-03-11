scalaVersion := "2.11.8"

version := "0.1.0"


// libraryDependencies ++= Seq(
//     "com.typesafe.akka" %% "akka-actor" % "2.5.13",
//     "com.typesafe.akka" %% "akka-stream" % "2.5.13",
//     "com.typesafe.akka" %% "akka-http" % "10.1.3",
//   )

// resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies  ++= Seq(

  "org.apache.spark" %% "spark-core" % "2.4.4" ,
  "org.apache.spark" %% "spark-sql" % "2.4.4" ,
  "org.apache.spark" %% "spark-mllib" % "2.4.4" , 
  
  // "com.datarobot" % "datarobot-mlops" % "6.0.0",
  // "com.datarobot" %% "scoring-code-spark-api" % "0.0.19"
)




// libraryDependencies ++= Seq(scalaVersion("org.scala-lang" % "scala-reflect" % _))

// libraryDependencies ++= Seq(scalaVersion("org.scala-lang" % "scalap" % _))



// libraryDependencies +=  "org.scalaj" %% "scalaj-http" % "2.4.2"
