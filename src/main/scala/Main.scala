/* Main.scala */

import java.io.File
import java.net.URI

import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.deploy.SparkHadoopUtil

case class SOIRow(Sn: String, Op: String, Actions: String, Tools: String, Materials: String)

object Main {

// Path hadoopPath(String fileName) throws IOException {
//      Configuration conf = SparkHadoopUtil.get().conf();
//      String yarnStagingDir = System.getenv("SPARK_YARN_STAGING_DIR");
//      Path target = null;
//      if(yarnStagingDir != null) {
//          FileSystem fs = FileSystem.get(conf);
//          target = new Path(new Path(fs.getHomeDirectory(), yarnStagingDir), new Path(fileName).getName());
//      }
//      return target;
//  }

  def getListOfFiles(dir: String):List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
//    d.listFiles.filter(_.isFile).toList
      d.listFiles.toList
    } else {
      List[File]()
    }
  }

  def recursiveListFiles(f: File): Array[File] = {
    val these = f.listFiles
    these ++ these.filter(_.isDirectory).flatMap(recursiveListFiles)
  }

  def main(args: Array[String]) {

    val conf = new SparkConf().setAppName("SOI Application").setMaster("local")

    val sc = new SparkContext(conf)

    val config = SparkHadoopUtil.get.conf
    val fs = FileSystem.get(config)

    val hd = fs.getHomeDirectory() 

    val status = fs.listStatus(new Path(hd + "/docs/"))

    // Create list of file paths

    def fpaths = for {
      file <- status
      //if file.getName.endsWith(".scala")
    } yield (file.getPath.toUri())

    val rowRDD = sc.parallelize(fpaths).map(path => SOI.extract(path))

    rowRDD.saveAsTextFile(java.util.UUID.randomUUID.toString)

    sc.stop()
  }
}
