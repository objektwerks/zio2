package objektwerks

import zio.test.*

object FilesTest extends ZIOSpecDefault:
  def spec = suite("file")(
    test("open") {
      Files.open("./LICENSE").map(content => assertTrue( content.length() == 9125 ))
    }
  )