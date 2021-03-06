package objektwerks

import zio.Queue
import zio.test._
import zio.test.Assertion.equalTo

object QueueTest extends ZioTest:
  def spec: Spec[Environment, TestFailure[Nothing], TestSuccess] = suite("queue.test")(
    test("queue") {
      for {
        queue  <- Queue.bounded[Int](3)
        _      <- queue.offer(3)
        result <- queue.take
      } yield assert( result )( equalTo(3) )
    }
  )