package objektwerks

import zio.{Console, Scope, UIO, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}
import java.io.IOException

// Service Trait
trait Logger:
  def log(message: String): UIO[Unit]

// Service Implementor
class DefaultLogger extends Logger:
  override def log(message: String): UIO[Unit] = Console.printLine(message).orDie

// Service ZLayer, No Accessor Method
object DefaultLogger:
  val layer: ZLayer[Any, Nothing, DefaultLogger] = ZLayer.succeed(DefaultLogger())

// App
object LoggerApp extends ZIOAppDefault:
  def app: ZIO[Logger, Throwable, Unit] =
    for
      logger <- ZIO.service[Logger]
      _      <- logger.log("*** Logger message!")
    yield()

  override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app.provide(DefaultLogger.layer)