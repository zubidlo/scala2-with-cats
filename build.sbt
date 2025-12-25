import sbt.Keys.scalacOptions

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.18"

lazy val root = (project in file("."))
  .settings(
    name := "scala2-cats",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "2.13.0",
      "org.typelevel" %% "cats-laws" % "2.13.0" % Test,
      "ch.qos.logback" % "logback-classic" % "1.5.23",
      "com.typesafe.scala-logging" %% "scala-logging" % "4.0.0-RC1",
      "org.scalatest" %% "scalatest" % "3.2.19" % "test",
      "org.scalatestplus" %% "scalacheck-1-19" % "3.2.19.0" % "test"
    ),
    scalacOptions ++= Seq(
      "-Xfatal-warnings",
      "-deprecation"
    )
  )
