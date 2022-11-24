package objektwerks

import zio.test.{assertTrue, ZIOSpecDefault}

object ResourceTest extends ZIOSpecDefault:
  def spec = suite("resource")(
    test("file") {
      Files.read("./LICENSE").map { lines =>
        assertTrue(lines.size == 48) && assertTrue(lines.mkString(" ").length() == 9124)
      }
    },
    test("url") {
      Resources.read("https://github.com/objektwerks").map { lines =>
        assertTrue(lines.nonEmpty)
      }
    }
  )