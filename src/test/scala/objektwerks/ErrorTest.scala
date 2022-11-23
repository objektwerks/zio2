package objektwerks

import java.io.IOException

import zio.ZIO
import zio.test.{assertTrue, ZIOSpecDefault}
import scala.concurrent.Future

object ErrorTest extends ZIOSpecDefault:
  def spec = suite("errors")(
    test("catchAll") {
      Files
        .read("./LIC")
        .catchAll( _ => Files.read("./LICENSE") )
        .map(lines => assertTrue(lines.size == 48))
    },
    test("catchSome") {
      Files
        .read("./LIC")
        .catchSome { case ioe: IOException => Files.read("./LICENSE") }
        .map(lines => assertTrue(lines.size == 48))
    },
    test("orElse") {
      Files
        .read("./LIC")
        .orElse( Files.read("./LICENSE") )
        .map(lines => assertTrue(lines.size == 48))
    },
    test("foldCause") {
      ZIO
        .attempt( 1 / 0 )
        .foldCauseZIO(
          cause => ZIO.succeed(-1),
          value => ZIO.succeed(value)
        )
        .map(value => assertTrue(value == -1))
    }
  )