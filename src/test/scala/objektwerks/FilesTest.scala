package objektwerks

import zio.test.{ assertTrue, ZIOSpecDefault }

object FilesTest extends ZIOSpecDefault:
  def spec = suite("file")(
    test("read") {
      val file = Files.read("./LICENSE")
      file.map(lines => assertTrue( lines.size == 48 ))
      file.map(lines => assertTrue( lines.mkString(" ").length() == 9124 ))
    }
  )