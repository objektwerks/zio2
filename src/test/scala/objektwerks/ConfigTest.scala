package objektwerks

import com.typesafe.config.ConfigFactory

import zio.ZIO
import zio.config.*
import zio.config.typesafe.*
import zio.config.magnolia.descriptor
import zio.test.{assertTrue, ZIOSpecDefault}

case class Conf(value: String, number: Int)

object ConfigTest extends ZIOSpecDefault:
  def spec = suite("files")(
    test("conf") {
      val config = ConfigFactory.load("test.conf")
      println(config)
      val source = TypesafeConfigSource.fromTypesafeConfig( ZIO.attempt( ConfigFactory.load("test.conf") ) )
      val zconf = read( descriptor[Conf].from(source) )
      for {
        conf <- zconf
      } yield {
        assertTrue(conf.value == "value")
        assertTrue(conf.number == 1)
      }
    }
  )