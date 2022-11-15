package objektwerks

import zio.{Console, Scope, Task, UIO, URIO, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

// Service Trait
trait Logger:
  def log(message: String): UIO[Unit]

// Service Accessor Method
object Logger:
  def log(message: String): URIO[Logger, Unit] = ZIO.serviceWith[Logger](_.log(message))

// Service Implementor
class LoggerLive extends Logger:
  override def log(message: String): UIO[Unit] = Console.printLine(message).orDie

// Service ZLayer
object LoggerLive:
  val layer: ZLayer[Any, Nothing, LoggerLive] = ZLayer.succeed(LoggerLive())

// App
object LoggerApp extends ZIOAppDefault:
  def app: ZIO[Logger, Throwable, Unit] =
    for
      _ <- Logger.log("Logger message!")
    yield()

  override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app.provide(LoggerLive.layer)