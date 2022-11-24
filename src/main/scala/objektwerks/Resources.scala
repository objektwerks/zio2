package objektwerks

import com.typesafe.config.{Config, ConfigFactory}

import java.io.IOException

import scala.io.{BufferedSource, Codec, Source}

import zio.{Scope, ZIO}

object Resources:
  private def acquire(url: => String): ZIO[Any, IOException, Source] =
    ZIO.attemptBlockingIO(Source.fromURL(url))

  private def release(source: => Source): ZIO[Any, Nothing, Unit] =
    ZIO.succeedBlocking(source.close())

  private def source(url: => String): ZIO[Scope, IOException, Source] =
    ZIO.acquireRelease(acquire(url))(release(_))

  def read(url: String): ZIO[Scope, IOException, List[String]] =
    ZIO.scoped {
      source(url).flatMap { source =>
        ZIO.attemptBlockingIO(source.getLines().toList)
      }
    }

  def loadConfig(path: String, section: String): ZIO[Any, IOException, Config] =
    ZIO.attemptBlockingIO(
      ConfigFactory
        .load(path)
        .getObject(section)
        .toConfig
    )