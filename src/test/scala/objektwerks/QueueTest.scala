package objektwerks

import zio.Queue
import zio.test.{assertTrue, ZIOSpecDefault}

object QueueTest extends ZIOSpecDefault:
  def spec = suite("queue")(
    test("queue") {
      for {
        queue  <- Queue.bounded[Int](1)
        _      <- queue.offer(1)
        result <- queue.take
      } yield assertTrue(result == 1)
    }
  )