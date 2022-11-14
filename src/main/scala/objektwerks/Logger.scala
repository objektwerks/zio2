package objektwerks

import zio.{Console, Scope, Task, UIO, URIO, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

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

// Service ZLayer, No Accessor Method
object Log:
  val layer: ZLayer[Any, Nothing, Log] = ZLayer.succeed(Log())

// 3. Root Service

// Service Implementor, No Trait
class LogHolder(logger: Logger, log: Log):
  def logAll: ZIO[Any, Nothing, Unit] =
    for {
      _ <- logger.log("*** LogHolder -> Logger log message!")
      _ <- log.log("*** LogHolder -> Log log message!")
    } yield ()

// Service ZLayer, No Accessor Method
object LogHolder:
  val layer: ZLayer[Logger with Log, Nothing, LogHolder] =
    ZLayer {
      for
        logger <- ZIO.service[Logger]
        log    <- ZIO.service[Log]
      yield LogHolder(logger, log)
    }

object LoggerLogApp extends ZIOAppDefault:
  def app(logHolder: LogHolder): ZIO[Any, Throwable, Unit] =
    for
      _ <- logHolder.logAll
    yield()

  override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] =
    ZIO
      .serviceWithZIO[LogHolder](app)
      .provide(LogHolder.layer, LoggerLive.layer, Log.layer)