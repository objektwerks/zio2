name := "zio2"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "3.4.2"
libraryDependencies ++= {
  val zioVersion = "2.1.1"
  val zioConfigVersion = "4.0.2"
  Seq(
    "dev.zio" %% "zio" % zioVersion,
    "dev.zio" %% "zio-http" % "0.0.5",
    "dev.zio" %% "zio-streams" % zioVersion,
    "dev.zio" %% "zio-json" % "0.6.2",
    "dev.zio" %% "zio-config" % zioConfigVersion,
    "dev.zio" %% "zio-config-typesafe" % zioConfigVersion,
    "dev.zio" %% "zio-config-magnolia" % zioConfigVersion,
    "dev.zio" %% "zio-logging" % "2.2.2",
    "dev.zio" %% "zio-cache" % "0.2.3",
    "dev.zio" %% "zio-direct" % "1.0.0-RC7",
    "io.getquill" %% "quill-jdbc-zio" % "4.8.3",
    "com.h2database" % "h2" % "2.2.224",
    "org.postgresql" % "postgresql" % "42.7.3",
    "org.scalafx" %% "scalafx" % "22.0.0-R33",
    compilerPlugin("com.github.ghik" % "zerowaste" % "0.2.18" cross CrossVersion.full),
    "org.slf4j" % "slf4j-nop" % "2.0.12",
    "dev.zio" %% "zio-test" % zioVersion % Test,
    "dev.zio" %% "zio-test-sbt" % zioVersion % Test
  )
}
scalacOptions ++= Seq(
  "-Wunused:all"
)
