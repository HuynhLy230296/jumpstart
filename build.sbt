val scala3Version = "3.3.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "jumpstart-20231114",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,
  )

libraryDependencies ++= Seq(
  "com.greenfossil" %% "thorium" % "0.7.9" withSources(),
  "org.slf4j" % "slf4j-api" % "2.0.5",
  "ch.qos.logback" % "logback-classic" % "1.4.7" % Test,
  "org.scalameta" %% "munit" % "0.7.29" % Test
)
