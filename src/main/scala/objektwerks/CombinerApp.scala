package objektwerks

import zio.Console.{printLine, readLine}
import zio.{Task, ZIO, ZIOAppDefault}

/*
NOTE: ZIO boilerplate not required for this app. Only Combiner class below is required.

import zio.{Task, URLayer, ZIO, ZLayer}

trait Combiner:
  def combine(a: String, b: String): Task[String]

class CombinerLive extends Combiner:
  override def combine(a: String, b: String): Task[String] = ZIO.attempt(a + b)

object Combiner:
  def combine(a: String, b: String): ZIO[Combiner, Nothing, Task[String]] = ZIO.serviceWith[Combiner](_.combine(a, b))

object CombinerLive:
  val layer: URLayer[Any, Combiner] = ZLayer.succeed(CombinerLive())
*/

class Combiner:
  def combine(a: String, b: String): Task[String] = ZIO.attempt(a + b)

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

  def run = app(Combiner())