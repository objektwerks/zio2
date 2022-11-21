package objektwerks

import zio.ZIO

case class Resource(url: String):
  def open: ZIO[Any, Nothing, String] = ZIO.succeed(s"opening resource: $url").debug
  def close: ZIO[Any, Nothing, String] = ZIO.succeed(s"closing resource: $url").debug

object Resource:
  def apply(url: String): ZIO[Any, Nothing, Resource] = ZIO.succeed(new Resource(url))