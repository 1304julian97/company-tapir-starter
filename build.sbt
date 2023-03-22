val tapirVersion = "1.2.2"

val DoobieVersion = "1.0.0-RC1"
val NewTypeVersion = "0.4.4"

lazy val rootProject = (project in file(".")).settings(
  Seq(
    name := "company",
    version := "0.1.0-SNAPSHOT",
    organization := "co.com.company",
    scalaVersion := "2.13.10",

    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.tapir" %% "tapir-http4s-server" % tapirVersion,
      "org.http4s" %% "http4s-blaze-server" % "0.23.12",
      "com.softwaremill.sttp.tapir" %% "tapir-prometheus-metrics" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % tapirVersion,
      "ch.qos.logback" % "logback-classic" % "1.4.5",
      "com.softwaremill.sttp.tapir" %% "tapir-sttp-stub-server" % tapirVersion % Test,
      "org.scalatest" %% "scalatest" % "3.2.14" % Test,
      "com.softwaremill.sttp.client3" %% "circe" % "3.8.3" % Test,
      "org.tpolecat" %% "doobie-core" % DoobieVersion,
      "org.tpolecat" %% "doobie-postgres" % DoobieVersion,
      "org.tpolecat" %% "doobie-hikari" % DoobieVersion,
      "io.estatico" %% "newtype" % NewTypeVersion,
      "co.fs2" %% "fs2-reactive-streams" % "3.4.0",
      "io.circe" %% "circe-fs2" % "0.14.0"
    ),
    scalacOptions += "-Wnonunit-statement"
  )
)
