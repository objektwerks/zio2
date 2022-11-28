package objektwerks

import zio.ZIOAppDefault

import zio.{Console, Runtime, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

case class Todo(id: Int = 0, task: String)

object QuillApp extends ZIOAppDefault:
  def app: ZIO[Any, Exception, Unit] =
    for
      _  <- Console.printLine("TODO!")
    yield ()

  def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app