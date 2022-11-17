/*
  Effect properties:
  - the type signature describes what KIND of computation it will perform
  - the type signature describes the type of VALUE that it will produce
  - if side effects are required, construction must be separate from the EXECUTION
*/

// Effect
val option: Option[Int] = Option(1)

// Side Effect
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
val aFuture: Future[Int] = Future(1)

