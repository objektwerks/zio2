import zio.{IO, RIO, Runtime, Task, UIO, URIO, ZIO}

// Type Aliases
val uio: UIO[Int] = ZIO.succeed(1)
val task: Task[Int] = ZIO.succeed(1)
val io: IO[String, Int] = ZIO.succeed(1)
val urio: URIO[Int, Int] = ZIO.succeed(1)
val rio: RIO[Int, Int] = ZIO.succeed(1)

// Runtime
def run(effect: ZIO[Any, Throwable, Boolean]): ZIO[Any, Throwable, Unit] =
  Runtime.default.run(effect).map(result => assert(result))

// Effects
val success = ZIO.succeed(1)
val failure = ZIO.fail("failure")
val suspend = ZIO.suspend(success)

run(
  success
    .map(value => value + 1)
    .map(result => result == 2)
)

run(
  success
    .flatMap(value => ZIO.succeed(value + 1))
    .map(result => result == 2)
)

run(
  ZIO
    .succeed(1)
    .zip(ZIO.succeed(1))
    .map(tuple => tuple._1 + tuple._2 == 2)
)

run(
  ZIO
    .succeed(1)
    .zipWith(ZIO.succeed(1))(_ + _)
    .map(result => result == 2)
)
