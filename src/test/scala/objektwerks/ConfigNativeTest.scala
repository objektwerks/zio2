package objektwerks

import zio.Config
import zio.test.{assertTrue, ZIOSpecDefault}

case class JavaHome(value: String)

object JavaHome {
  val config: Config[JavaHome] = 
    Config.string("value").map { 
      case value => JavaHome(value)
    }
}

object ConfigNativeTest extends ZIOSpecDefault:
  def spec = suite("config-native")(
    test("conf") {
      assertTrue(true)
    }
  )