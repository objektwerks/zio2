package objektwerks

import zio.{Console, Random, Runtime, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}
import zio.logging.console

object RandomApp extends ZIOAppDefault:
  override val bootstrap: ZLayer[ZIOAppArgs, Any, Environment] =
    Runtime.removeDefaultLoggers >>> console()

  def app: ZIO[Any, Exception, Unit] =
    for
      ri <- Random.nextInt
      _  <- ZIO.log(s"Random int: $ri")
      _  <- Console.printLine(s"Random int: $ri")
    yield ()

  def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app