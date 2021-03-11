

## set the output type. as is, this will write to stdout
## change to OUTPUT_DIR to write to /tmp/ta
# export MLOPS_OUTPUT_TYPE=STDOUT

## below is the way you must specify the spooler type and filesystem.  
# export MLOPS_SPOOLER_TYPE=STDOUT
# export MLOPS_SPOOLER_TYPE=FILESYSTEM
export MLOPS_SPOOLER_TYPE=OUTPUT_DIR
export MLOPS_FILESYSTEM_DIRECTORY=/tmp/ta

# Configure the maximum number of files and the maximum file size for output files recording the statistics.
# If your prediction traffic is bursty, consider having more, larger files.
# If your environment is space-constrained, you may require a smaller file size.
export MLOPS_SPOOLER_MAX_FILES=5
export MLOPS_SPOOLER_FILE_MAX_SIZE=1045876000

# export MLOPS_DEPLOYMENT_ID=602452645ac4a2841a9e746d
# export MLOPS_MODEL_ID=6024524146c1d9e079ee4522

export MLOPS_DEPLOYMENT_ID=60426b60cabb2340b6fa03cd
export MLOPS_MODEL_ID=60426ac29453ef27ab850c60

## should already have spark installed.  if not installed it!

$SPARK_HOME/bin/spark-submit --jars ./lib/5d5da72a3fa59e2850f824fc.jar,./lib/datarobot-mlops-6.2.4.jar,./lib/scoring-code-spark-api_2.4.3-0.0.17-SNAPSHOT.jar ./target/scala-2.11/spark-agents_2.11-0.1.0.jar 5