package objektwerks

import zio.test.{assertTrue, ZIOSpecDefault}

object ConfigNativeTest extends ZIOSpecDefault:
  def spec = suite("config-native")(
    test("conf") {
      assertTrue(true)
    }
  )