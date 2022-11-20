package objektwerks

import zio.{Exit, ZIO}
import zio.test.{assertTrue, ZIOSpecDefault}

object FiberTest extends ZIOSpecDefault:
  def spec = suite("fiber")(
    test("fiber join") {
      for
        helloFiber <- ZIO.succeed("Hello, ").fork
        worldFiber <- ZIO.succeed("world!").fork
        tuple      <- (helloFiber zip worldFiber).join
      yield
        val (hello, world) = tuple
        assertTrue(hello + world == "Hello, world!")
    },
    test("fiber await") {
      for
        helloFiber <- ZIO.succeed("Hello, ").fork
        worldFiber <- ZIO.succeed("world!").fork
        result     <- (helloFiber zip worldFiber).await
      yield result match
        case Exit.Success(tuple) =>
          val (hello, world) = tuple
          assertTrue(hello + world == "Hello, world!")
        case Exit.Failure(cause) => assert(false, s"failed with $cause")
    },
    test("fiber orElse") {
      for
        failFiber    <- ZIO.fail(1).fork
        succeedFiber <- ZIO.succeed(2).fork
        value        <- (failFiber orElse succeedFiber).join
      yield assertTrue(value == 2)
    }    
  )