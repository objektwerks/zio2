package objektwerks

import zio.ZIO
import zio.config.read
import zio.config.ReadError
import zio.config.magnolia.descriptor
import zio.test.{assertTrue, ZIOSpecDefault}

case class Conf(value: String, number: Int)

object ConfigTest extends ZIOSpecDefault:
  def loadConf(path: String, section: String): ZIO[Any, ReadError[zio.config.K], Conf] =
    read( descriptor[Conf].from( Resources.loadConfigSource("test.conf", "Conf") ) ).map(conf => conf)

  def spec = suite("config")(
    test("conf") {
      loadConf("test.conf", "Conf").map { case Conf(value, number) =>
        assertTrue(value == "value") && assertTrue(number == 1)
      }
    }
  )