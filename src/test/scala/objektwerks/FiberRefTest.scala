package objektwerks

import zio.FiberRef
import zio.test.{assertTrue, ZIOSpecDefault}

object FiberRefTest extends ZIOSpecDefault {
  def spec = suite("fiber.ref")(
    test("fiber.ref") {
      for
        fiberRef <- FiberRef.make(1)
        initial  <- fiberRef.get
        _        <- fiberRef.set(initial * 2)
        result   <- fiberRef.get
      yield assertTrue(result == 2)
    }
  )
}