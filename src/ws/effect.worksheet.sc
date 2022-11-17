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

val myIOSideEffect: MyIO[Int] = MyIO(() => {
  println("MyIO is producing an effect ...")
  1
})
myIOSideEffect.map(result => assert(result == 1))