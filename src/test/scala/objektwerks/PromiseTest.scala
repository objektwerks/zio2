package objektwerks

import zio.Promise
import zio.test.{assertTrue, ZIOSpecDefault}

object PromiseTest extends ZIOSpecDefault:
  def spec = suite("promise")(
    test("promise") {
      for
        promise <- Promise.make[Throwable, Int]
        succeed <- promise.succeed(1)
        _       <- promise.await
      yield assertTrue(succeed)
    }
  )