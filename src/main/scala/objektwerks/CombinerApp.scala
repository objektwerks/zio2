package objektwerks

import zio.Console.{printLine, readLine}
import zio.{Task, ZIO, ZIOAppDefault, ZLayer}

class Combiner:
  def combine(a: String, b: String): Task[String] = ZIO.attempt(a + b)

object Combiner:
  val layer: ZLayer[Any, Nothing, Combiner] = ZLayer.succeed(Combiner())

object CombinerApp extends ZIOAppDefault:
  def app(combiner: Combiner): Task[Unit] =
    for {
      _ <- printLine("Enter a string value:")
      a <- readLine
      _ <- printLine("Enter another string value:")
      b <- readLine
      c <- combiner.combine(a, b)
      _ <- printLine(s"Both values combined: $c")
    } yield ()

  // Note, Combiner ZLayer not used.
  def run = app(Combiner())