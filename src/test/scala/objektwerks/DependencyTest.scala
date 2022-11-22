package objektwerks

import zio.{durationInt, Console, ZIO}
import zio.test.{assertZIO, TestConsole, ZIOSpecDefault}
import zio.test.Assertion.containsString
import zio.test.TestAspect.timeout

object DependencyTest extends ZIOSpecDefault:
  def spec = suite("dependency")(
    test("provide layer") {
      assertZIO(
        for
          combiner <- ZIO.service[Combiner]
          _        <- Console.printLine("Enter a value:")
          _        <- TestConsole.feedLines("abc")
          a        <- Console.readLine
          _        <- Console.printLine("Enter another value:")
          _        <- TestConsole.feedLines("123")
          b        <- Console.readLine
          c        <- combiner.combine(a, b)
          _        <- Console.printLine(s"Combined value: $c")
        yield c
      )(containsString("abc123"))
    }.provideLayer(Combiner.layer) @@ timeout(3.seconds)
  )