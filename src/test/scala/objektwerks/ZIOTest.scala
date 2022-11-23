package objektwerks

import scala.concurrent.Future
import scala.util.Try

import zio.ZIO
import zio.test.{assertZIO, ZIOSpecDefault}
import zio.test.Assertion.*

object ZIOTest extends ZIOSpecDefault:
  def spec = suite("effect")(
    test("map") {
      assertZIO(
        ZIO
          .succeed(1)
          .map(value => value + 1)
      )(equalTo(2))
    },
    test("flatMap") {
      assertZIO(
        ZIO
          .succeed(1)
          .flatMap(value => ZIO.succeed(value + 1))
      )(equalTo(2))
    },
    test("zip") {
      assertZIO(
        ZIO
          .succeed(1)
          .zip(ZIO.succeed(1))
          .map(tuple => tuple._1 + tuple._2)
      )(equalTo(2))
    },
    test("zipWith") {
      assertZIO(
        ZIO
          .succeed(1)
          .zipWith(ZIO.succeed(1))(_ + _)
      )(equalTo(2))
    },
    test("exit") {
      assertZIO(
        ZIO
          .succeed(1)
          .map(value => value + 1)
          .exit
          .map(exit => exit.isSuccess)
      )(isTrue)
    },
    test("fromFuture") {
      assertZIO(
        ZIO
          .fromFuture { implicit ec =>
            Future(1 * 2)
          }
      )(equalTo(2))
    },
    test("fromTry") {
      assertZIO(
        ZIO.fromTry( Try(2 / 2) )
      )(equalTo(1))
    },
    test("fromEither") {
      assertZIO(
        ZIO.fromEither( Right(1) )
      )(equalTo(1))
    },
    test("fromOption") {
      assertZIO(
        ZIO.fromOption( Some(1) )
      )(equalTo(1))
    },
    test("attempt > foldZIO") {
      assertZIO(
        ZIO
          .attempt( 1 / 1 )
          .foldZIO(
            error => ZIO.succeed(0),
            value => ZIO.succeed(value)
          )
      )(equalTo(1))
    },
  )