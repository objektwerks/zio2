package objektwerks

import zio.{Task, URLayer, ZIO, ZLayer}

trait Combiner:
  def combine(a: String, b: String): Task[String]

class CombinerLive extends Combiner:
  override def combine(a: String, b: String): Task[String] = ZIO.attempt(s"$a$b")

// CombinerApp does not appear to require these objects.

object Combiner:
  def combine(a: String, b: String): ZIO[Combiner, Nothing, Task[String]] = ZIO.serviceWith[Combiner](_.combine(a, b))

object CombinerLive:
  val layer: URLayer[Any, Combiner] = ZLayer.succeed(CombinerLive())