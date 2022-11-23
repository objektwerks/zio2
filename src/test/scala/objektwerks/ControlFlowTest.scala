package objektwerks

import zio.test.ZIOSpecDefault

import zio.{ZIO}

import zio.test.{assertZIO, ZIOSpecDefault}
import zio.test.Assertion.*

object ControlFlowTest extends ZIOSpecDefault:
  def isGreaterThanZero(value: Int): ZIO[Any, String, Int] =
    if (value > 0)
      ZIO.succeed(value)
    else
      ZIO.fail(s"Less than or equal to zero: $value")

  def spec = suite("control flow")(
    test("if") {
      assertZIO(
        isGreaterThanZero(1)
      )(equalTo(1))
    }
  )