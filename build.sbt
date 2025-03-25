name := "zio2"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "3.6.4" // 3.7.0-RC1 breaks ScalaFx!
libraryDependencies ++= {
  val zioVersion = "2.1.16"
  val zioConfigVersion = "4.0.4"
  Seq(
    "dev.zio" %% "zio" % zioVersion,
    "dev.zio" %% "zio-http" % "3.0.1",
    "dev.zio" %% "zio-streams" % zioVersion,
    "dev.zio" %% "zio-json" % "0.7.39",
    "dev.zio" %% "zio-config" % zioConfigVersion,
    "dev.zio" %% "zio-config-typesafe" % zioConfigVersion,
    "dev.zio" %% "zio-config-magnolia" % zioConfigVersion,
    "dev.zio" %% "zio-logging" % "2.5.0",
    "dev.zio" %% "zio-cache" % "0.2.4",
    "dev.zio" %% "zio-direct" % "1.0.0-RC7",
    "io.getquill" %% "quill-jdbc-zio" % "4.8.6",
    "com.h2database" % "h2" % "2.3.232",
    "org.postgresql" % "postgresql" % "42.7.5",
    "org.scalafx" %% "scalafx" % "23.0.1-R34",
    "org.slf4j" % "slf4j-nop" % "2.0.16",
    "dev.zio" %% "zio-test" % zioVersion % Test,
    "dev.zio" %% "zio-test-sbt" % zioVersion % Test
  )
}
scalacOptions ++= Seq(
  "-Wunused:all"
)
