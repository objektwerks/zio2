package objektwerks

import zio.ZIO
import zio.config.read
import zio.config.magnolia.descriptor
import zio.config.typesafe.TypesafeConfigSource
import zio.test.{assertTrue, ZIOSpecDefault}

case class Conf(value: String, number: Int)

object ConfigTest extends ZIOSpecDefault:
  def spec = suite("config")(
    test("conf") {
      read( descriptor[Conf].from( Resources.loadConfigSource("test.conf", "Conf") ) ).map { conf =>
        assertTrue(conf.value == "value") && assertTrue(conf.number == 1)
      }
    }
  )