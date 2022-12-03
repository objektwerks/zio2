name := "zio2"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "3.2.1"
libraryDependencies ++= {
  val zioVersion = "2.0.4"
  val zioConfigVersion = "3.0.1"
  Seq(
    "dev.zio" %% "zio" % zioVersion,
    "dev.zio" %% "zio-http" % "0.0.3",
    "dev.zio" %% "zio-streams" % zioVersion,
    "dev.zio" %% "zio-json" % "0.3.0",
    "dev.zio" %% "zio-config" % zioConfigVersion,
    "dev.zio" %% "zio-config-typesafe" % zioConfigVersion,
    "dev.zio" %% "zio-config-magnolia" % zioConfigVersion,
    "dev.zio" %% "zio-logging" % "2.1.5",
    "dev.zio" %% "zio-cache" % "0.2.0",
    "io.getquill" %% "quill-jdbc-zio" % "4.6.0",
    "com.h2database" % "h2" % "2.1.214",
    compilerPlugin("com.github.ghik" % "zerowaste" % "0.2.1" cross CrossVersion.full),
    "dev.zio" %% "zio-test" % zioVersion % Test,
    "dev.zio" %% "zio-test-sbt" % zioVersion % Test
  )
}