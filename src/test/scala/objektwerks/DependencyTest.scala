package objektwerks

import zio.{ZIO, ZLayer}
import zio.test.{assertZIO, ZIOSpecDefault}
import zio.test.Assertion.*

object DependencyTest extends ZIOSpecDefault:
  def spec = suite("dependency")(
    test("provide layer") {
      assertZIO(
        ZIO.succeed(1)
      )(equalTo(1))
    }.provideLayer(ZLayer.succeed(1))
  )