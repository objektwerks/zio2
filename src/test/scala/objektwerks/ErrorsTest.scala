package objektwerks

import zio.test.{assertTrue, ZIOSpecDefault}
import java.io.IOException

object ErrorsTest extends ZIOSpecDefault:
  def spec = suite("errors")(
    test("catchAll") {
      Files.read("./LIC").catchAll( _ =>
        Files.read("./LICENSE")
      ).map { lines =>
        assertTrue(lines.size == 48)
      }
    },
    test("catchSome") {
      Files.read("./LIC").catchSome { case ioe: IOException =>
        Files.read("./LICENSE")
      }.map { lines =>
        assertTrue(lines.size == 48)
      }
    }
  )