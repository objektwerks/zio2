package objektwerks

import zio.{Console, Runtime, Scope, System, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

/**
  * System.env("JAVA_HOME") fails in a ZIO test. Why?
  */
object SystemApp extends ZIOAppDefault:
  def app: ZIO[Any, Exception, Unit] =
    for
      opt <- System.env("JAVA_HOME")
      jh  =  opt.getOrElse("System env failed!")
      _   <- ZIO.log(s"JAVA_HOME = $jh")
      _   <- Console.printLine(s"JAVA_HOME = $jh")
    yield assert(jh != "System env failed!")

  def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app