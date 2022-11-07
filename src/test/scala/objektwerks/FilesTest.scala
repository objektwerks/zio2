package objektwerks

import zio.test._

object FilesTest extends ZIOSpecDefault:
  def spec = suite("file")(
    test("open") {
      Files.open("./LICENSE").map(content => assertTrue( content.nonEmpty ))
    }
  )