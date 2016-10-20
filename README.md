# Hadoop on McBook

/usr/local/Cellar/hadoop/2.7.3/libexec/etc/hadoop/hadoop-env.sh,
/usr/local/Cellar/hadoop/2.7.3/libexec/etc/hadoop/mapred-env.sh and
 /usr/local/Cellar/hadoop/2.7.3/libexec/etc/hadoop/yarn-env.sh

## A ne faire qu'une fois
hdfs namenode -format

## demarre hdfs
start-dfs.sh 

## create dir
hdfs dfs -mkdir /user
hdfs dfs -mkdir /user/tog


## How to run the app
spark-submit --executor-cores 2 --num-executors 2 --master yarn-cluster --class Main  --archives target/universal/sparkextract-1.0.zip#prout --jars target/universal/sparkextract-1.0/lib/org.apache.poi.poi-3.14.jar,target/universal/sparkextract-1.0/lib/org.apache.poi.poi-scratchpad-3.14.jar,target/universal/sparkextract-1.0/lib/org.datanucleus.datanucleus-api-jdo-5.0.3.jar,target/universal/sparkextract-1.0/lib/org.datanucleus.datanucleus-core-5.0.3.jar,target/universal/sparkextract-1.0/lib/org.datanucleus.datanucleus-rdbms-5.0.3.jar target/scala-2.10/sparkextract_2.10-1.0.jar

# On cluster

sbt clean universal:packageBin

spark-submit --executor-cores 2 --num-executors 2 --master yarn-cluster --class Main  --archives target/universal/sparkextract-1.0.zip#prout --conf spark.driver.extraClassPath='prout/sparkextract-1.0/lib/*'  --conf spark.executor.extraClassPath='prout/sparkextract-1.0/lib/*' target/scala-2.10/sparkextract_2.10-1.0.jar

The file is generated in a directory which name comes from UUID
Use

hdfs dfs -ls

in order to find the directory name

And then a command similar to this one to list the content of the file

hdfs dfs -cat  9d240391-1ed5-4071-9285-55861e59c17b/part-00000
