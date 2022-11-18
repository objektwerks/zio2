package objektwerks

import zio.{ZIO}
import zio.test.{assertTrue, ZIOSpecDefault}

object EffectTest extends ZIOSpecDefault:
  def spec = suite("effect")(
    test("effects") {
      ZIO
        .succeed(1)
        .map(value => value + 1)
        .map(result => assertTrue(result == 2))
    }
  )