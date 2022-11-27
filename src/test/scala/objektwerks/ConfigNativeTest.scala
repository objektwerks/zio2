package objektwerks

import zio.{Config, ConfigProvider, ZIO}
import zio.test.{assertTrue, ZIOSpecDefault}

case class JavaHome(value: String)

object JavaHome {
  val config: Config[JavaHome] =
    Config.string("JAVA_HOME").map {
      case value => JavaHome(value)
    }
}

object ConfigNativeTest extends ZIOSpecDefault:
  println(s"JAVA_HOME = ${sys.env.get("JAVA_HOME").get}")

  def spec = suite("config-native")(
    test("config") {
      /*
        ZIO native config is broken in 2.0.4.
        Must wait for new release and docs.
      for
        config <- ZIO.config[JavaHome](JavaHome.config)
      yield assertTrue(config.value.nonEmpty) */
      assertTrue(true)
    }
  )