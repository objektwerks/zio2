package objektwerks

import zio.{Console, UIO, URIO, ZIO, ZLayer}

// Official ZIO Service Pattern
// Only a Service Implementer and Service ZLayer is required, though.

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