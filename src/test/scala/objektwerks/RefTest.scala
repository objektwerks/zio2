package objektwerks

import zio.Ref
import zio.test._
import zio.test.Assertion.equalTo

object RefTest extends ZioTest:
  def spec: Spec[Environment, TestFailure[Nothing], TestSuccess] = suite("ref.test")(
    test("ref") {
      for {
        ref     <- Ref.make(3)
        initial <- ref.get
        _       <- ref.set(initial * 3)
        result  <- ref.get
      } yield assert( result )( equalTo(9) )
    }
  )