package objektwerks

import zio.ZIOAppDefault

import zio.{Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

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
  def a: ZIO[A, Nothing, String] = ZIO.serviceWithZIO[A](_.a)

  val layer: ZLayer[Any, Nothing, A] = ZLayer.succeed(apply())

object BDefault:
  def b: ZIO[B, Nothing, String] = ZIO.serviceWithZIO[B](_.b)

  val layer: ZLayer[Any, Nothing, B] = ZLayer.succeed(apply())

object CDefault:
  def c: ZIO[C, Nothing, String] = ZIO.serviceWithZIO[C](_.c)

  def abc: ZIO[C, Nothing, String] = ZIO.serviceWithZIO[C](_.abc)

  val layer: ZLayer[B with A, Nothing, C] =
    ZLayer {
      for {
        a <- ZIO.service[A]
        b <- ZIO.service[B]
      } yield CDefault(a, b)
    }

object ServicePatternApp extends ZIOAppDefault:
  val app: ZIO[A with B with C, Nothing, Boolean] =
    for {
      a <- ZIO.service[A]
      b <- ZIO.service[B]
      c <- ZIO.service[C]
      as <- a.a.debug
      bs <- b.b.debug
      cs <- c.c.debug
      abc <- c.abc.debug
    } yield (as + bs + cs) == abc

  val appLayer: ZLayer[Any, Nothing, A with B with C] =
    ZLayer.make[A with B with C](ADefault.layer, BDefault.layer, CDefault.layer)


  override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app.provide(appLayer)