package objektwerks

import zio.{durationInt, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

object ParallelismApp extends ZIOAppDefault:
  val fibera = ZIO.succeed("fiber a running...").debug *> ZIO.sleep(1.second)
  val fiberb = ZIO.succeed("fiber b running ...").debug *> ZIO.sleep(1.second)
  val effect = fibera.zipPar(fiberb)

  val app =
    for
      fiber  <- effect.fork
      result <- fiber.join
    yield result

  override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app