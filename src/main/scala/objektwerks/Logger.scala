package objektwerks

import zio.{Console, UIO, URIO, ZIO, ZLayer}

// 1. Official ZIO Service Pattern

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

// 2. Simple ZIO Service Pattern
 
// Service Implementor, No Trait
class Log:
  def log(message: String): UIO[Unit] = Console.printLine(message).orDie

// Service ZLayer, No Acessor Method
object Log:
  val layer: ZLayer[Any, Nothing, Log] = ZLayer.succeed(Log())