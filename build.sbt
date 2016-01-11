name := """sms-partners"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test,
  "com.twilio.sdk" % "twilio-java-sdk" % "5.8.0",
  "com.typesafe.play" %% "play-slick" % "1.1.1",
  "com.typesafe.play" %% "play-slick-evolutions" % "1.1.1",
  "com.typesafe.slick" %% "slick-codegen" % "3.1.1",
  "net.codingwell" %% "scala-guice" % "4.0.1",
  "mysql" % "mysql-connector-java" % "5.1.34"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

slick <<= slickCodeGenTask

// code generation task
lazy val slick = TaskKey[Seq[File]]("gen-tables")
lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
  val outputDir = dir.getPath // place generated files in sbt's managed sources folder
val url = "jdbc:mysql://localhost:8889/sms_partners" // connection info for a pre-populated throw-away, in-memory db for this demo, which is freshly initialized on every run
val jdbcDriver = "com.mysql.jdbc.Driver"
  val slickDriver = "slick.driver.MySQLDriver"
  val pkg = "ch.wavein.sms_partners.models.schema"
  toError(r.run("slick.codegen.SourceCodeGenerator", cp.files, Array(slickDriver, jdbcDriver, url, outputDir, pkg, "root", "root"), s.log))
  val fname = outputDir + "ch/wavein/sms_partners/models/schema/Tables.scala"
  Seq(file(fname))
}

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
