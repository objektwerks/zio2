name := "zio2"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "3.2.1"
libraryDependencies ++= {
  val zioVersion = "2.0.4"
  val zioConfigVersion = "3.0.1"
  Seq(
    "dev.zio" %% "zio" % zioVersion,
    "io.d11" %% "zhttp" % "2.0.0-RC11",
    "dev.zio" %% "zio-streams" % zioVersion,
    "dev.zio" %% "zio-json" % "0.3.0",
    "dev.zio" %% "zio-config" % zioConfigVersion,
    "dev.zio" %% "zio-config-typesafe" % zioConfigVersion,
    "dev.zio" %% "zio-config-magnolia" % zioConfigVersion,
    compilerPlugin("com.github.ghik" % "zerowaste" % "0.2.1" cross CrossVersion.full),
    "dev.zio" %% "zio-test" % zioVersion % Test,
    "dev.zio" %% "zio-test-sbt" % zioVersion % Test
  )
}