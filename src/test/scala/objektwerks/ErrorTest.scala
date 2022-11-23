package objektwerks

import java.io.IOException

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Try

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
    test("fromFuture") {
      ZIO
        .fromFuture( Future(1 / 0) )
        .catchAll(_ => ZIO.succeed(0))
        .map(value => assertTrue(value == 0))
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
    },
    test("fromOption") {
      ZIO
        .fromOption( Some(1) )
        .map(value => assertTrue(value == 1))
    },
    test("foldZIO") {
      ZIO
        .attempt( 1 / 0 )
        .foldZIO(
          error => ZIO.succeed(0),
          value => ZIO.succeed(value)
        )
        .map(value => assertTrue(value == 0))
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