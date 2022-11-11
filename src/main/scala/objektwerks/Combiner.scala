package objektwerks

import zio.{Task, URLayer, ZIO, ZLayer}

trait Combiner:
  def combine(a: String, b: String): Task[String]

object Combiner:
  def combine(a: String, b: String): ZIO[Combiner, Nothing, Task[String]] = ZIO.serviceWith[Combiner](_.combine(a, b))

class CombinerLive extends Combiner:
  override def combine(a: String, b: String): Task[String] = ZIO.attempt(s"$a $b")

object CombinerLive:
  val layer: URLayer[Any, Combiner] = ZLayer.succeed(CombinerLive())