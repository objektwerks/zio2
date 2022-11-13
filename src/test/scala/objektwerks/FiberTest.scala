package objektwerks

import zio.ZIO
import zio.test.{assertTrue, ZIOSpecDefault}

object FiberTest extends ZIOSpecDefault:
  def spec = suite("fiber")(
    test("fiber") {
      for
        helloFiber <- ZIO.succeed("Hello, ").fork
        worldFiber <- ZIO.succeed("world!").fork
        tuple      <- (helloFiber zip worldFiber).join
      yield
        val (hello, world) = tuple
        assertTrue(hello + world == "Hello, world!")
    }
  )