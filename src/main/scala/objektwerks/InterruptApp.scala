package objektwerks

import zio.{durationInt, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

object InterruptApp extends ZIOAppDefault:
  val effect: ZIO[Any, Nothing, String] = {
    ZIO.succeed("fiber running...").debug
    *> ZIO.sleep(1.second)
    *> ZIO.succeed("fiber completed!").debug
  }.onInterrupt(ZIO.succeed("fiber interrupted!").debug)

  val app: ZIO[Any, Nothing, String] =
    for
      fiber  <- effect.fork
      _      <- ZIO.succeed("interrupting fiber...").debug
      _      <- fiber.interrupt
      _      <- ZIO.succeed("interrupted fiber!").debug
      result <- fiber.join
    yield result

  override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app