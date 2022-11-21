package objektwerks

import zio.{durationInt, Scope, Task, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

object ParallelismApp extends ZIOAppDefault:
  val effect = {
    ZIO.succeed("fiber 1 running...").debug
    *> ZIO.sleep(1.second)
    *> ZIO.succeed("fiber 2 running ...").debug
    *> ZIO.sleep(1.second)
    *> ZIO.succeed("fiber 1 and 2 completed!").debug
  }.onInterrupt(ZIO.succeed("fiber 1 and/or 2 interrupted!").debug)

  val app =
    for
      fiber  <- effect.fork
      result <- fiber.join
    yield result

  override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app