package objektwerks

import zio.{Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

object ParApp extends ZIOAppDefault:
  val zios: Seq[ZIO[Any, Nothing, Int]] = (1 to 1_000_000).map(i => ZIO.succeed(i))
  
  val effect: ZIO[Any, Nothing, Int] = ZIO.reduceAllPar(ZIO.succeed(0), zios)(_ + _)

  val app: ZIO[Any, Nothing, Unit] =
    for
      fiber  <- effect.fork
      result <- fiber.join
      _      <- ZIO.succeed(f"Par reduce: $result").debug
    yield ()

  override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app