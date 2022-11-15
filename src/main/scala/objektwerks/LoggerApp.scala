package objektwerks

import zio.{Console, IO, Scope, UIO, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}
import java.io.IOException

// Service Trait
trait Logger:
  def log(message: String): IO[IOException, Unit]

// Service Implementor
class DefaultLogger extends Logger:
  override def log(message: String): IO[IOException, Unit] = Console.printLine(message)

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