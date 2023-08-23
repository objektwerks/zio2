name := "zio2"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "3.3.1-RC5"
libraryDependencies ++= {
  val zioVersion = "2.0.16"
  val zioConfigVersion = "3.0.7"
  Seq(
    "dev.zio" %% "zio" % zioVersion,
    "dev.zio" %% "zio-http" % "0.0.5",
    "dev.zio" %% "zio-streams" % zioVersion,
    "dev.zio" %% "zio-json" % "0.6.1",
    "dev.zio" %% "zio-config" % zioConfigVersion,
    "dev.zio" %% "zio-config-typesafe" % zioConfigVersion,
    "dev.zio" %% "zio-config-magnolia" % zioConfigVersion,
    "dev.zio" %% "zio-logging" % "2.1.14",
    "dev.zio" %% "zio-cache" % "0.2.3",
    "dev.zio" %% "zio-direct" % "1.0.0-RC7",
    "io.getquill" %% "quill-jdbc-zio" % "4.6.1",
    "com.h2database" % "h2" % "2.2.220",
    "org.postgresql" % "postgresql" % "42.6.0",
    "org.scalafx" %% "scalafx" % "20.0.0-R31",
    // Unavailable for latest Scala version. compilerPlugin("com.github.ghik" % "zerowaste" % "0.2.6" cross CrossVersion.full),
    "org.slf4j" % "slf4j-nop" % "2.0.7",
    "dev.zio" %% "zio-test" % zioVersion % Test,
    "dev.zio" %% "zio-test-sbt" % zioVersion % Test
  )
}
scalacOptions ++= Seq(
  "-Wunused:all"
)
