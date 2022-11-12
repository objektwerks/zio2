package objektwerks

import com.typesafe.config.ConfigFactory

import zio.ZIO
import zio.config.*
import zio.config.magnolia.descriptor
import zio.config.typesafe.TypesafeConfigSource
import zio.test.{assertTrue, ZIOSpecDefault}

case class Conf(string: String, integer: Int)

object ConfigTest extends ZIOSpecDefault:
  def spec = suite("files")(
    test("conf") {
      val source = TypesafeConfigSource.fromTypesafeConfig( ZIO.attempt( ConfigFactory.load("test.conf") ) )
      val zconf = read( descriptor[Conf].from(source) )
      zconf.map { conf => 
        assertTrue(conf.string == "string")
        assertTrue(conf.integer == 1)
      }
    }
  )