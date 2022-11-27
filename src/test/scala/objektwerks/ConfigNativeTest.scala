package objektwerks

import zio.{Config, ConfigProvider, ZIO}
import zio.test.{assertTrue, ZIOSpecDefault}

case class JavaHome(value: String)

object JavaHome {
  def config: Config[JavaHome] = 
    Config.string("JAVA_HOME").map {
      case value => JavaHome(value)
    }
}

object ConfigNativeTest extends ZIOSpecDefault:
  def spec = suite("config-native")(
    test("config") {
      ConfigProvider
        .envProvider
        .load(JavaHome.config)
        .tap(javaHome => ZIO.succeed(javaHome.toString).debug)
        .map(javaHome => assertTrue(javaHome.value.nonEmpty))
    }
  )