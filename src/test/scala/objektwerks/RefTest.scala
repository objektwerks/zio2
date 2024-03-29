package objektwerks

import zio.{FiberRef, Ref}
import zio.test.{assertTrue, ZIOSpecDefault}

object RefTest extends ZIOSpecDefault:
  def spec = suite("ref")(
    test("get > set > get") {
      for
        ref     <- Ref.make(1)
        initial <- ref.get
        _       <- ref.set(initial + 1)
        result  <- ref.get
      yield assertTrue(result == 2)
    },
    test("getAndSet > get") {
      for
        ref    <- Ref.make(1)
        _      <- ref.getAndSet(2)
        result <- ref.get
      yield assertTrue(result == 2)
    },
    test("update > get") {
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
    },
    test("modify") {
      for
        ref    <- Ref.make(1)
        result <- ref.modify(i => (i + 1, i))
      yield assertTrue(result == 2)
    },
    test("fiber ref > updateAndGet") {
      for
        fiberRef <- FiberRef.make(1)
        result   <- fiberRef.updateAndGet(_ + 1)
      yield assertTrue(result == 2)
    }
  )