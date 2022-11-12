package objektwerks

import com.typesafe.config.ConfigFactory

import zio.ZIO
import zio.config.*
import zio.config.typesafe.*
import zio.config.magnolia.descriptor
import zio.test.{assertTrue, ZIOSpecDefault}

case class Conf(value: String, number: Int)

object ConfigTest extends ZIOSpecDefault:
  def spec = suite("config")(
    test("conf") {
      val config = ConfigFactory.load("test.conf").getObject("Conf").toConfig
      val source = TypesafeConfigSource.fromTypesafeConfig( ZIO.attempt(config) )
      read( descriptor[Conf].from(source) ).map { conf =>
        assertTrue(conf.value == "value")
        assertTrue(conf.number == 1)
      }
    }
  )