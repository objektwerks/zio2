package objektwerks

import zio.ZIO
import zio.test.{assertTrue, ZIOSpecDefault}

case class Resource(url: String):
  def open(): ZIO[Any, Nothing, String] = ZIO.succeed(s"opening resource: $url").debug
  def close(): ZIO[Any, Nothing, String] = ZIO.succeed(s"closing resource: $url").debug

object Resource:
  def apply(url: String): ZIO[Any, Nothing, Resource] = ZIO.succeed(new Resource(url))

object ResourceTest extends ZIOSpecDefault:
  def spec = suite("resources")(
    test("file") {
      Files.read("./LICENSE").map { lines =>
        assertTrue(lines.size == 48) && assertTrue(lines.mkString(" ").length() == 9124)
      }
    }
  )