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
  def map[B](f: A => B): IO[B] =
    IO(() => f(unsafeRun()))

  def flatMap[B](f: A => IO[B]): IO[B] =
    IO(() => f(unsafeRun()).unsafeRun())

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
