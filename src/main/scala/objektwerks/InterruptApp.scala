package objektwerks

import zio.{durationInt, Scope, Task, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

object InterruptApp extends ZIOAppDefault:
  val app =
    for
      f <- ZIO.succeed("fiber").fork
      _ <- ZIO.sleep(1.second) *> ZIO.succeed("interrupting fiber...").debug *> f.interruptFork
      _ <- ZIO.succeed("interrupted fiber!").debug
      r <- f.join
    yield r

  override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app