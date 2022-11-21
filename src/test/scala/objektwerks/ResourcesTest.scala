package objektwerks

import zio.test.{assertTrue, ZIOSpecDefault}

object ResourcesTest extends ZIOSpecDefault:
  def spec = suite("resources")(
    test("file read") {
      Files.read("./LICENSE").map { lines =>
        assertTrue(lines.size == 48) && assertTrue(lines.mkString(" ").length() == 9124)
      }
    }
  )