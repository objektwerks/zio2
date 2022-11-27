package objektwerks

import zio.ZIOAppDefault

import zio.{Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

// Service Traits
trait A:
  def a: ZIO[Any, Nothing, String]

trait B:
  def b: ZIO[Any, Nothing, String]

trait C:
  def c: ZIO[Any, Nothing, String]
  def abc: ZIO[Any, Nothing, String]

// Service Implementors
final case class ADefault() extends A:
  def a = ZIO.succeed("a")

final case class BDefault() extends B:
  def b: ZIO[Any, Nothing, String] = ZIO.succeed("b")

final case class CDefault(a: A, b: B) extends C:
  def c: ZIO[Any, Nothing, String] = ZIO.succeed("c")
  def abc: ZIO[Any, Nothing, String] =
    for
      a <- a.a
      b <- b.b
      c <- c
    yield a + b + c

object ServicePatternApp extends ZIOAppDefault:
  override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = ???