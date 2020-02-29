name := "Import"

version := "0.1"

scalaVersion := "2.12.10"

val sparkVersion = "3.0.0-preview"
val sqliteVersion = "3.30.1"

resolvers ++= Seq(
  "bintray-spark-packages" at "https://dl.bintray.com/spark-packages/maven",
  "Typesafe Simple Repository" at "https://repo.typesafe.com/typesafe/simple/maven-releases",
  "MavenRepository" at "https://mvnrepository.com"
)


libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,

  // logging
  "org.apache.logging.log4j" % "log4j-api" % "2.13.0",
  "org.apache.logging.log4j" % "log4j-core" % "2.13.0",

  // sqlite3
  "org.xerial" % "sqlite-jdbc" % sqliteVersion
)