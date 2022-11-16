package objektwerks

import zio.Console.{printLine, readLine}
import zio.{Scope, Task, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

// Service Implementor, No Trait
class Combiner:
  def combine(a: String, b: String): Task[String] = ZIO.attempt(a + b)

// Service ZLayer, No Accessor Method
object Combiner:
  val layer: ZLayer[Any, Nothing, Combiner] = ZLayer.succeed(Combiner())

object CombinerApp extends ZIOAppDefault:
  def app(combiner: Combiner): ZIO[Any, Throwable, Unit] =
    for
      _ <- printLine("*** Enter a string value:")
      a <- readLine
      _ <- printLine("*** Enter another string value:")
      b <- readLine
      c <- combiner.combine(a, b)
      _ <- printLine(s"*** The combined values equal: $c")
    yield()

  override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] =
    ZIO
      .serviceWithZIO[Combiner](app)
      .provide(Combiner.layer)