package objektwerks

import com.typesafe.config.ConfigFactory

import zio.config.*
import zio.config.magnolia.descriptor
import zio.config.typesafe.TypesafeConfigSource
import zio.test.{assertTrue, ZIOSpecDefault}

case class Conf(string: String, integer: Int)

object ConfTest extends ZIOSpecDefault:
  def spec = suite("files")(
    test("conf") {
      for {
        source <- TypesafeConfigSource.fromTypesafeConfig(ConfigFactory.load("./test.conf"))
        conf   <- read( descriptor[Conf] from source )
      } yield {
        assertTrue(conf.string == "string")
        assertTrue(conf.integer = 1)
      }
    }
  )