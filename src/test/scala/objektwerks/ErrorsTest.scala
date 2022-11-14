package objektwerks

import zio.test.{assertTrue, ZIOSpecDefault}

object ErrorsTest extends ZIOSpecDefault:
  def spec = suite("errors")(
    test("catchAll") {
      Files.read("./LIC").catchAll( _ =>
        Files.read("./LICENSE")
      ).map { lines =>
        assertTrue(lines.size == 48)
      }
    }
  )