package objektwerks

import scala.io.{BufferedSource, Codec, Source}

import zio.{Task, ZIO}

object Files {
  def open(path: String): Task[String] =
    ZIO.scoped {
      ZIO.acquireReleaseWith(
        ZIO.attemptBlocking(Source.fromFile(path))
      )(file => close(file).orDie) { source => read(source) }
    }

  def read(source: BufferedSource): Task[String] = ZIO.attemptBlocking(source.mkString)

  def close(source: BufferedSource): Task[Unit] = ZIO.attempt(source.close)
}