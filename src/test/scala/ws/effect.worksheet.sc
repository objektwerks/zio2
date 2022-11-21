import zio.{IO, RIO, Runtime, Task, UIO, URIO, ZIO}

/*
  Effect Properties
  - Type signature describes what kind of computation it will perform.
  - Type signature describes the type of value that it will produce.
  - If side effects are required, construction must be separate from the execution.
*/

// Effect
val option: Option[Int] = Option(1)

// Side Effect
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
val future: Future[Int] = Future(1)

// Effect Monad
case class IO[A](unsafeRun: () => A):
  def map[B](f: A => B): IO[B] = IO(() => f(unsafeRun()))
  def flatMap[B](f: A => IO[B]): IO[B] = IO(() => f(unsafeRun()).unsafeRun())

IO(() => 1).unsafeRun()
IO(() => System.currentTimeMillis()).unsafeRun()

// Effect Monad
case class MIO[-R, +E, +A](unsafeRun: R => Either[E, A]):
  def map[B](f: A => B): MIO[R, E, B] =
    MIO(r => unsafeRun(r) match
      case Right(value) => Right(f(value))
      case Left(error) => Left(error)
    )

  def flatMap[R1 <: R, E1 >: E, B](f: A => MIO[R1, E1, B]): MIO[R1, E1, B] =
    MIO(r => unsafeRun(r) match
      case Right(value) => f(value).unsafeRun(r)
      case Left(error) => Left(error)
    )

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
run(
  ZIO
    .succeed(1)
    .map(value => value + 1)
    .map(result => result == 2)
)

run(
  ZIO
    .succeed(1)
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