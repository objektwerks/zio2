package objektwerks

import java.io.IOException

import zio.{Console, Random, Runtime, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}
import zio.logging.console

object RandomApp extends ZIOAppDefault:
  override val bootstrap: ZLayer[ZIOAppArgs, Any, Environment] =
    Runtime.removeDefaultLoggers >>> console()

  def app: ZIO[Any, IOException, Unit] =
    for
      ri <- Random.nextInt
      _  <- ZIO.log(s"Random int: $ri")
      _  <- Console.printLine(s"Random int: $ri")
    yield assert(ri != 0)

  def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app