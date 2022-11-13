package objektwerks

import zio.Ref
import zio.test.{assertTrue, ZIOSpecDefault}

object RefTest extends ZIOSpecDefault:
  def spec = suite("ref")(
    test("ref") {
      for
        ref     <- Ref.make(1)
        initial <- ref.get
        _       <- ref.set(initial * 2)
        result  <- ref.get
      yield assertTrue(result == 2)
    }
  )