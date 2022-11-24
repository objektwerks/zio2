package objektwerks

import com.typesafe.config.ConfigFactory

import com.typesafe.config.Config

import java.io.IOException

import zio.ZIO
import zio.config.read
import zio.config.magnolia.descriptor
import zio.config.typesafe.TypesafeConfigSource
import zio.test.{assertTrue, ZIOSpecDefault}

case class Conf(value: String, number: Int)

object ConfigTest extends ZIOSpecDefault:
  def loadConfig(path: String, section: String): ZIO[Any, IOException, Config] =
    ZIO.attemptBlockingIO(
      ConfigFactory
        .load(path)
        .getObject(section)
        .toConfig
    )

  def spec = suite("config")(
    test("conf") {
      val source = TypesafeConfigSource.fromTypesafeConfig( loadConfig("test.conf", "Conf") )
      read( descriptor[Conf].from(source) ).map { conf =>
        assertTrue(conf.value == "value") && assertTrue(conf.number == 1)
      }
    }
  )