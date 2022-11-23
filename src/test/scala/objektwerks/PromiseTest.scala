package objektwerks

import zio.Promise
import zio.test.{assertTrue, ZIOSpecDefault}

object PromiseTest extends ZIOSpecDefault:
  def spec = suite("promise")(
    test("succeed") {
      for
        promise <- Promise.make[Throwable, Int]
        succeed <- promise.succeed(1)
        _       <- promise.await
      yield assertTrue(succeed)
    },
    test("fail") {
      (for
        promise <- Promise.make[Throwable, Int]
        fail    <- promise.fail(new IllegalArgumentException("invalid arg"))
        _       <- promise.await
      yield assertTrue(fail == false)).catchAll(failure => assertTrue(failure.getMessage.nonEmpty))
    }
  )