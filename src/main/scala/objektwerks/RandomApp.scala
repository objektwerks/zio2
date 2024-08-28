package objektwerks

import java.io.IOException

import zio.{Console, Random, Runtime, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

object RandomApp extends ZIOAppDefault:
  def app: ZIO[Any, IOException, Unit] =
    for
      ri <- Random.nextInt
      _  <- ZIO.log(s"Random int: $ri")
      _  <- Console.printLine(s"Random int: $ri")
    yield assert(ri != 0)

  def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app