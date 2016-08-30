name := "SparkExtract"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
    "org.apache.spark" %% "spark-core" % "1.5.2",
    "org.apache.poi" % "poi" % "3.14",
    "org.apache.poi" % "poi-scratchpad" % "3.14"
)