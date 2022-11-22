package objektwerks

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import zio.{Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

object AsyncApp extends ZIOAppDefault:
  val effect: ZIO[Any, Throwable, Int] = ZIO.fromFuture( Future(1 + 1) )

  val app: ZIO[Any, Throwable, Int] =
    for
      _      <- ZIO.succeed("Future calculating...").debug
      result <- effect
       _     <- ZIO.succeed(s"Future calculated: $result").debug
    yield result
    
  override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app