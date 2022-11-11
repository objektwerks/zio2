package objektwerks

import java.io.IOException

import scala.io.{BufferedSource, Codec, Source}

import zio.{Scope, Task, ZIO}

object Files:
  private def acquire(path: => String): ZIO[Any, IOException, Source] =
    ZIO.attemptBlockingIO(Source.fromFile(path))

  private def release(source: => Source): ZIO[Any, Nothing, Unit] =
    ZIO.succeedBlocking(source.close())

  private def source(name: => String): ZIO[Scope, IOException, Source] =
    ZIO.acquireRelease(acquire(name))(release(_))

  def read(path: String): ZIO[Scope, IOException, List[String]] =
    ZIO.scoped {
      source(path).flatMap { source =>
        ZIO.attemptBlockingIO(source.getLines().toList)
      }
    }