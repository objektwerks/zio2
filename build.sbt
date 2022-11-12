name := "zio2"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "3.2.2-RC1"
libraryDependencies ++= {
  val zioVersion = "2.0.3"
  val zioConfigVersion = "3.0.1"
  Seq(
    "dev.zio" %% "zio" % zioVersion,
    "dev.zio" %% "zio-managed" % zioVersion,
    "dev.zio" %% "zio-streams" % zioVersion,
    "io.d11" %% "zhttp" % "2.0.0-RC11",
    "dev.zio" %% "zio-json" % "0.3.0",
    "dev.zio" %% "zio-config" % zioConfigVersion,
    "dev.zio" %% "zio-config-typesafe" % zioConfigVersion,
    "dev.zio" %% "zio-config-magnolia" % zioConfigVersion,
    "dev.zio" %% "zio-test" % zioVersion % Test,
    "dev.zio" %% "zio-test-sbt" % zioVersion % Test
  )
}