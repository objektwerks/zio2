package objektwerks

import scala.io.{BufferedSource, Codec, Source}

import zio.{Task, ZIO}

object Files {
  def open(file: String): Task[String] =
    ZIO
      .attempt(Source.fromFile(file, Codec.UTF8.name))
      .acquireReleaseWith(close(_).ignore)(source => read(source))

  def read(source: BufferedSource): Task[String] = ZIO.attempt(source.mkString)

  def close(source: BufferedSource): Task[Unit] = ZIO.attempt(source.close)

  def file(file: String): Task[String] = open(file)
}