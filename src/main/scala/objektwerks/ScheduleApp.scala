package objektwerks

import zio.{Random, Schedule, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

object ScheduleApp extends ZIOAppDefault:
  val effect: ZIO[Any, String, String] = Random.nextBoolean.flatMap { isTrue =>
    if isTrue then ZIO.succeed("random boolean is true").debug
    else ZIO.succeed("random boolean is false").debug *> ZIO.fail("random effect failed")
  }

  val app: ZIO[Any, String, String] = effect.retry(Schedule.recurs(5))
    
  override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app