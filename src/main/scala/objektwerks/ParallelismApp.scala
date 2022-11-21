package objektwerks

import zio.{durationInt, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

object ParallelismApp extends ZIOAppDefault:
  val zioa = ZIO.succeed("zio a running...").debug *> ZIO.sleep(1.second)
  val ziob = ZIO.succeed("zio b running ...").debug *> ZIO.sleep(1.second)
  val effect = zioa.zipPar(ziob)

  val app =
    for
      fiber  <- effect.fork
      result <- fiber.join
    yield result

  override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app