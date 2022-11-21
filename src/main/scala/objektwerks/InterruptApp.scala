package objektwerks

import zio.{durationInt, Scope, Task, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

object InterruptApp extends ZIOAppDefault:
  val effect = {
    ZIO.succeed("fiber running...").debug
    *> ZIO.sleep(1.second)
    *> ZIO.succeed("fiber completed!").debug
  }.onInterrupt(ZIO.succeed("fiber interrupted!")).debug

  val app =
    for
      f <- effect.fork
      _ <- ZIO.succeed("interrupting fiber...").debug
      _ <- f.interruptFork
      _ <- ZIO.succeed("interrupted fiber!").debug
      r <- f.join
    yield r

  override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app