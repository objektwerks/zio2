package objektwerks

import zio.{Clock, Console, Runtime, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}
import zio.logging.console

object ClockApp extends ZIOAppDefault:
  override val bootstrap: ZLayer[ZIOAppArgs, Any, Environment] =
    Runtime.removeDefaultLoggers >>> console()

  def app: ZIO[Any, Exception, Unit] =
    for
      dt <- Clock.currentDateTime
      _  <- ZIO.log(s"Clock datetime: $dt")
      _  <- Console.printLine(s"Clock datetime: $dt")
    yield ()

  def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app