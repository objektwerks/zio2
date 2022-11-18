package objektwerks

import java.io.IOException

import scala.util.Try

import zio.ZIO
import zio.test.{assertTrue, ZIOSpecDefault}

object ErrorsTest extends ZIOSpecDefault:
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
    test("fromTry") {
      ZIO
        .fromTry( Try(1 / 0) )
        .catchAll(_ => ZIO.succeed(0))
        .map(value => assertTrue(value == 0))
    },
    test("fromEither") {
      ZIO
        .fromEither( Right(1) )
        .mapAttempt(value => assertTrue(value == 1))
    }
  )