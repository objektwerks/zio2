name := "zio2"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "3.2.2-RC1"
libraryDependencies ++= {
  val zioVersion = "2.0.3"
  Seq(
    "dev.zio" %% "zio" % zioVersion,
    "dev.zio" %% "zio-managed" % zioVersion,
    "dev.zio" %% "zio-streams" % zioVersion,
    "io.d11" %% "zhttp" % "2.0.0-RC11",
    "dev.zio" %% "zio-json" % "0.3.0",
    "dev.zio" %% "zio-test" % zioVersion % Test,
    "dev.zio" %% "zio-test-sbt" % zioVersion % Test
  )
}
parallelExecution := false
testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
