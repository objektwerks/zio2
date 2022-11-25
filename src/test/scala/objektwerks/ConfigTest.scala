package objektwerks

import zio.ZIO
import zio.config.{K, read, ReadError}
import zio.config.magnolia.descriptor
import zio.test.{assertTrue, ZIOSpecDefault}

case class Conf(value: String, number: Int)

object ConfigTest extends ZIOSpecDefault:
  def loadConf(path: String, section: String): ZIO[Any, ReadError[K], Conf] =
    read( descriptor[Conf].from( Resources.loadConfigSource(path, section) ) )

  def spec = suite("config")(
    test("conf") {
      loadConf(path = "test.conf", section = "Conf").map { case Conf(value, number) =>
        assertTrue(value == "value" && number == 1)
      }
    }
  )