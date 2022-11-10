package objektwerks

import scala.io.{BufferedSource, Codec, Source}

import zio.{Task, ZIO}

object Files:
  def open(path: String): Task[String] =
    ZIO.scoped {
      ZIO.acquireReleaseWith(
        ZIO.attemptBlocking(Source.fromFile(path))
      )(source => close(source).orDie) { source => read(source) }
    }

  private def read(source: BufferedSource): Task[String] = ZIO.attemptBlocking(source.mkString)

  private def close(source: BufferedSource): Task[Unit] = ZIO.attempt(source.close)