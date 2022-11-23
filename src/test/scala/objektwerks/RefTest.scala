package objektwerks

import zio.Ref
import zio.test.{assertTrue, ZIOSpecDefault}

object RefTest extends ZIOSpecDefault:
  def spec = suite("ref")(
    test("get > set") {
      for
        ref     <- Ref.make(1)
        initial <- ref.get
        _       <- ref.set(initial + 1)
        result  <- ref.get
      yield assertTrue(result == 2)
    },
    test("getAndSet") {
      for
        ref    <- Ref.make(1)
        _      <- ref.getAndSet(2)
        result <- ref.get
      yield assertTrue(result == 2)
    },
    test("update") {
      for
        ref    <- Ref.make(1)
        _      <- ref.update(_ + 1)
        result <- ref.get
      yield assertTrue(result == 2)
    },
    test("updateAndGet") {
      for
        ref    <- Ref.make(1)
        result <- ref.updateAndGet(_ + 1)
      yield assertTrue(result == 2)
    }
  )