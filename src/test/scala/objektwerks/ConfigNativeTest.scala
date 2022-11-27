package objektwerks

import zio.{Config, ConfigProvider, ZIO}
import zio.test.{assertTrue, ZIOSpecDefault}

case class JavaHome(value: String)

object JavaHome {
  def config: Config[JavaHome] =
    Config.succeed(sys.env.get("JAVA_HOME")).map {
      case value => JavaHome(value.getOrElse("JAVA_HOME not found"))
    }
}

object ConfigNativeTest extends ZIOSpecDefault:
  println(s"JAVA_HOME = ${sys.env.get("JAVA_HOME").get}")

  def spec = suite("config-native")(
    test("config") {
      for
        config <- ZIO.config[JavaHome](JavaHome.config)
      yield assertTrue(config.value.nonEmpty)
    }
  )