package objektwerks

import zio.ZIO
import zio.test.{assertTrue, assertZIO, ZIOSpecDefault}
import zio.test.Assertion.*
import zio.ExitCode

object EffectTest extends ZIOSpecDefault:
  def spec = suite("effect")(
    test("map") {
      assertZIO(
        ZIO.succeed(1).map(value => value + 1)
      )(equalTo(2))
    },
    test("flatMap") {
      assertZIO(
        ZIO.succeed(1).flatMap(value => ZIO.succeed(value + 1))
      )(equalTo(2))
    },
    test("zip") {
      assertZIO(
        ZIO.succeed(1).zip(ZIO.succeed(1)).map(tuple => tuple._1 + tuple._2)
      )(equalTo(2))
    },
    test("zipWith") {
      assertZIO(
        ZIO.succeed(1).zipWith(ZIO.succeed(1))(_ + _)
      )(equalTo(2))
    },
    test("exit") {
      assertZIO(
        ZIO.succeed(1).map(value => value + 1).exit.map(exit => exit.isSuccess)
      )(isTrue)
    },
  )