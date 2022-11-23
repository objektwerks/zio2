package objektwerks

import zio.test.ZIOSpecDefault

import zio.{ZIO}

import zio.test.{assertZIO, ZIOSpecDefault}
import zio.test.Assertion.*

object ControlFlowTest extends ZIOSpecDefault:
  def ifGreaterThanZero(value: Int): ZIO[Any, String, Int] =
    if (value > 0) ZIO.succeed(value) else ZIO.fail(s"Less than or equal to zero: $value")

  def whenGreaterThanZero(value: Int): ZIO[Any, Nothing, Option[Int]] =
    ZIO.when(value > 0)(ZIO.succeed(value))

  def spec = suite("control flow")(
    test("if") {
      assertZIO(
        ifGreaterThanZero(1)
      )(equalTo(1))
    },
    test("when") {
      assertZIO(
        whenGreaterThanZero(1)
      )(isSome)
    }
  )