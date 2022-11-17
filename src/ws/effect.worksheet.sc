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
val aFuture: Future[Int] = Future(1)

