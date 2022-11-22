package objektwerks

import zio.test.{assertTrue, check, Gen, ZIOSpecDefault}

object PropertyBasedTest extends ZIOSpecDefault:
  def add(x: Int, y: Int): Int = x + y

  def spec = suite("property based")(
    test("commutative") {
      check(Gen.int, Gen.int) { (x, y) =>
        assertTrue(add(x, y) == add(y, x))
      }
    }
  )