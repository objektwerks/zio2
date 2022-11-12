package objektwerks

import zio.config.*
import zio.config.magnolia.Descriptor.*
import zio.test.{assertTrue, ZIOSpecDefault}

case class Conf(string: String, integer: Int)

object ConfTest extends ZIOSpecDefault:
  def spec = suite("files")(
    test("conf") {
      val conf = descriptor[Conf]
      assertTrue(conf.string == "string")
      assertTrue(conf.integer = 1)
    }
  )