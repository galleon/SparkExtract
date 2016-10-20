name := "SparkExtract"

enablePlugins(JavaAppPackaging)

version := "1.0"

scalaVersion := "2.10.4"
//scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
//  "org.apache.spark" %% "spark-core" % "2.0.0" % "provided",
//  "org.apache.spark" %% "spark-hive" % "2.0.0" % "provided",
    "org.apache.spark" %% "spark-core" % "1.5.2" % "provided",
    "org.apache.spark" %% "spark-hive" % "1.5.2" % "provided",
//  "org.apache.hive" % "hive-metastore" % "1.2.1" % "provided",
    "org.datanucleus" % "datanucleus-api-jdo" % "5.0.3",
    "org.datanucleus" % "datanucleus-rdbms" % "5.0.3",
    "org.datanucleus" % "datanucleus-core" % "5.0.3",
    "org.apache.hadoop" % "hadoop-client" % "2.7.2" % "provided",
    "org.apache.poi" % "poi" % "3.14",
    "org.apache.poi" % "poi-scratchpad" % "3.14"
)

resolvers += Resolver.mavenLocal

assemblyMergeStrategy in assembly := {
  case PathList("org","aopalliance", xs @ _*) => MergeStrategy.last
  case PathList("javax", "inject", xs @ _*) => MergeStrategy.last
  case PathList("javax", "servlet", xs @ _*) => MergeStrategy.last
  case PathList("javax", "activation", xs @ _*) => MergeStrategy.last
  case PathList("org", "apache", xs @ _*) => MergeStrategy.last
  case PathList("com", "google", xs @ _*) => MergeStrategy.last
  case PathList("com", "esotericsoftware", xs @ _*) => MergeStrategy.last
  case PathList("com", "codahale", xs @ _*) => MergeStrategy.last
  case PathList("com", "yammer", xs @ _*) => MergeStrategy.last
  case "about.html" => MergeStrategy.rename
  case "META-INF/ECLIPSEF.RSA" => MergeStrategy.last
  case "META-INF/mailcap" => MergeStrategy.last
  case "META-INF/mimetypes.default" => MergeStrategy.last
  case "plugin.properties" => MergeStrategy.last
  case "log4j.properties" => MergeStrategy.last
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
