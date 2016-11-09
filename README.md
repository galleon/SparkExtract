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


# On cluster

I dont use anymore sbt but gradle (http://gradle.org)
sbt clean universal:packageBin

Therefore, you should use:
 - gradle distZip

The build.gradle assume that you have a local maven repository.

To run the code, use:

- spark-submit --executor-cores 2 --num-executors 2 --master yarn-cluster --class Main --archives build/distributions/SparkExtract-0.1-SNAPSHOT.zip#myLibs --conf spark.driver.extraClassPath='myLibs/SparkExtract-0.1-SNAPSHOT/lib/' --conf spark.executor.extraClassPath='myLibs/SparkExtract-0.1-SNAPSHOT/lib/' --jars ../myRepo/poi-scratchpad-3.14.jar,../myRepo/poi-3.14.jar   build/libs/SparkExtract-0.1-SNAPSHOT.jar

The result file is generated in a directory which name comes from UUID
Use

hdfs dfs -ls

in order to find the directory name

And then a command similar to this one to list the content of the file

hdfs dfs -cat  9d240391-1ed5-4071-9285-55861e59c17b/part-00000
