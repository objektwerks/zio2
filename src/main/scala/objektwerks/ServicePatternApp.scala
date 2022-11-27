package objektwerks

import zio.ZIOAppDefault

import zio.{Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

trait A:
  def a: ZIO[Any, Nothing, String]
trait B:
  def b: ZIO[Any, Nothing, String]
trait C:
  def c: ZIO[Any, Nothing, String]
  def abc: ZIO[Any, Nothing, String]

object ServicePatternApp extends ZIOAppDefault:
  override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = ???