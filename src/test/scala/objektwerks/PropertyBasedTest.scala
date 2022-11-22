package objektwerks

import zio.test.{assertTrue, ZIOSpecDefault}

object PropertyBasedTest extends ZIOSpecDefault:
  def spec = suite("property based")(
    test("property") {
      assertTrue(true)
    }
  )