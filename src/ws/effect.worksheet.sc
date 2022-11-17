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
case class MyIO[A](unsafeRun: () => A) {
  def map[B](f: A => B): MyIO[B] =
    MyIO(() => f(unsafeRun()))

  def flatMap[B](f: A => MyIO[B]): MyIO[B] =
    MyIO(() => f(unsafeRun()).unsafeRun())
}

MyIO(() => {
  1
}).map(result => assert(result == 1))

MyIO(() => System.currentTimeMillis()).map(result => assert(result > 0))
