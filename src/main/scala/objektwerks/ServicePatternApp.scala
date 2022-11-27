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

import zio._

// Service Layers
object ADefault:
  val layer: ZLayer[Any, Nothing, ADefault] = ZLayer.succeed(apply())

object BDefault:
  val layer: ZLayer[Any, Nothing, BDefault] = ZLayer.succeed(apply())

object CDefault:
  val layer: ZLayer[B with A, Nothing, CDefault] =
    ZLayer {
      for {
        a <- ZIO.service[A]
        b <- ZIO.service[B]
      } yield CDefault(a, b)
    }

object ServicePatternApp extends ZIOAppDefault:
  override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = ???