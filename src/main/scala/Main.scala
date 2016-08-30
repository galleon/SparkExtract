/* Main.scala */

import java.io.File

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object Main {
  def main(args: Array[String]) {

    val dir = "/Users/tog/Work/soi/docx"
    val files = new File(dir).listFiles().map(file => file.getAbsolutePath)

    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
    val sc = new SparkContext(conf)

    val filesRdd = sc.parallelize(files)
    filesRdd.foreach(filePath => SOI.extract(filePath))
  }
}