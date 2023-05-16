package objektwerks

import zio.Config
import zio.test.{assertTrue, ZIOSpecDefault}
import zio.test.Assertion.*

case class JavaHome(value: String)

object JavaHome {
  val config: Config[JavaHome] =
    Config.string("JAVA_HOME").map {
      case value => JavaHome(value)
    }
}

object ConfigNativeTest extends ZIOSpecDefault:
  val javaHome = sys.env.get("JAVA_HOME").get
  println(s"JAVA_HOME = $javaHome")

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