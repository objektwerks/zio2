package objektwerks

import zio.test.{assertTrue, ZIOSpecDefault}

object FilesTest extends ZIOSpecDefault:
  def spec = suite("files")(
    test("read") {
      Files.read("./LICENSE").map { lines =>
        assertTrue(lines.size == 48) && assertTrue(lines.mkString(" ").length() == 9124)
      }
    }
  )