name := "zio2"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "3.1.3-RC5"
libraryDependencies ++= {
  val zioVersion = "2.0.0-RC6"
  Seq(
    "dev.zio" %% "zio" % zioVersion,
    "dev.zio" %% "zio-managed" % zioVersion,
    "dev.zio" %% "zio-streams" % zioVersion,
    "dev.zio" %% "zio-test" % zioVersion % Test,
    "dev.zio" %% "zio-test-sbt" % zioVersion % Test
  )
}
parallelExecution := false
testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
