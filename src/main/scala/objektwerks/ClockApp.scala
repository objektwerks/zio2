package objektwerks

import java.io.IOException

import zio.{Clock, Console, Runtime, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

object ClockApp extends ZIOAppDefault:
  def app: ZIO[Any, IOException, Unit] =
    for
      dt <- Clock.currentDateTime
      _  <- ZIO.log(s"Clock datetime: $dt")
      _  <- Console.printLine(s"Clock datetime: $dt")
    yield assert(dt.toString.nonEmpty)

  def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app