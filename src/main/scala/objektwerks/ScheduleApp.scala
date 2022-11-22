package objektwerks

import zio.{durationInt, Random, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

object ScheduleApp extends ZIOAppDefault:
  val effect = Random.nextBoolean.flatMap { isTrue =>
    if isTrue then ZIO.succeed("is true").debug
    else ZIO.succeed("is false").debug *> ZIO.fail("failure")
  }

  val app =
    for
      result  <- effect
    yield result
    
  override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app