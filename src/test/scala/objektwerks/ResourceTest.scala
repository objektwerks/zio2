package objektwerks

import zio.ZIO
import zio.test.{assertTrue, ZIOSpecDefault}

object ResourceTest extends ZIOSpecDefault:
  def spec = suite("resource")(
    test("file") {
      Files.read("./LICENSE").map { lines =>
        assertTrue(lines.size == 48) && assertTrue(lines.mkString(" ").length() == 9124)
      }
    },
    test("resource") {
      for
        resource <- Resource("objektwerks.com")
        open     <- resource.open
        close    <- resource.close
      yield assertTrue(open.contains("opening")) && assertTrue(close.contains("closing"))
    }
  )