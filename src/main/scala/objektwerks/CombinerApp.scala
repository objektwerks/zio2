package objektwerks

import zio.Console.{printLine, readLine}
import zio.{Task, URLayer, ZIO, ZIOAppDefault, ZLayer}

trait Combiner:
  def combine(a: String, b: String): Task[String]

class CombinerLive extends Combiner:
  override def combine(a: String, b: String): Task[String] = ZIO.attempt(a + b)

object Combiner:
  def combine(a: String, b: String): ZIO[Combiner, Nothing, Task[String]] = ZIO.serviceWith[Combiner](_.combine(a, b))

object CombinerLive:
  val layer: URLayer[Any, Combiner] = ZLayer.succeed(CombinerLive())

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

  // Note, ZIO boilerplate is not required. Only CombinerLive is required.
  def run = app(CombinerLive())