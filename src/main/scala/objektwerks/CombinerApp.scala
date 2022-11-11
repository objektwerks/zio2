package objektwerks

import zio.Console.{printLine, readLine}
import zio.{Task, ZIOAppDefault}

object CombinerApp extends ZIOAppDefault:
  def app(combiner: Combiner): Task[Unit] =
        for {
      _ <- printLine("Enter a string:")
      a <- readLine
      _ <- printLine("Enter another string:")
      b <- readLine
      c <- combiner.combine(a, b)
      _ <- printLine(c)
    } yield ()

  def run = app(CombinerLive())