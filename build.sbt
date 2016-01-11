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
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "net.codingwell" %% "scala-guice" % "4.0.1",
  "mysql" % "mysql-connector-java" % "5.1.34"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
