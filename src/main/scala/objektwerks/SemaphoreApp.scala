package objektwerks

import java.io.IOException

import zio.{durationInt, Console, Scope, Semaphore, ZIO, ZIOAppArgs, ZIOAppDefault}

object SemaphoreApp extends ZIOAppDefault:
  val task = for
    _ <- Console.printLine("task starting...").debug
    _ <- ZIO.sleep(1.seconds)
    _ <- Console.printLine("task completed!").debug
  yield ()

  val sempahoreTask = (semaphore: Semaphore) => for
    _ <- semaphore.withPermit(task)
  yield ()

  val semaphoreTasks = (sempahore: Semaphore) => (1 to 2).map(_ => sempahoreTask(sempahore))

  val app: ZIO[Any, IOException, Unit] = for
    semaphore <- Semaphore.make(permits = 1)
    tasks     <- ZIO.succeed(semaphoreTasks(semaphore))
    _         <- ZIO.collectAllPar(tasks)
  yield ()

  override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app