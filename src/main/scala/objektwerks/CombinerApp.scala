package objektwerks

import zio.Console.{printLine, readLine}
import zio.ZIOAppDefault

object CombinerApp extends ZIOAppDefault:
  def run =
    for {
      _ <- printLine("Enter a string:")
      a <- readLine
      _ <- printLine("Enter another string:")
      b <- readLine
      _ <- printLine( Combiner.combine(a, b) )
    } yield ()