package objektwerks

import zio.{Task, ZIO}

import scala.io.{BufferedSource, Codec, Source}

object Resources {
  def resource(file: String): Task[BufferedSource] = ZIO.attempt(Source.fromFile(file, Codec.UTF8.name))

  def stringify(source: BufferedSource): Task[String] = ZIO.attempt(source.mkString)

  def close(source: BufferedSource): Task[Unit] = ZIO.attempt(source.close)
}