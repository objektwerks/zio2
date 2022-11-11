package objektwerks

import zio.{Task, URLayer, ZIO, ZLayer}

trait Combiner:
  def combine(x: String, y: String): Task[String]

object Combiner:
  def add(x: String, y: String): ZIO[Combiner, Nothing, Task[String]] = ZIO.serviceWith[Combiner](_.combine(x, y))

class CombinerLive extends Combiner:
  override def combine(x: String, y: String): Task[String] = ZIO.attempt(x + y)

object CombinerLive:
  val layer: URLayer[Any, Combiner] = ZLayer.succeed(CombinerLive())