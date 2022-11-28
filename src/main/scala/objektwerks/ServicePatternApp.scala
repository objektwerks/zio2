package objektwerks

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

// Service Accessors
object A:
  def a: ZIO[A, Nothing, String] = ZIO.serviceWithZIO[A](_.a)

object B:
  def b: ZIO[B, Nothing, String] = ZIO.serviceWithZIO[B](_.b)

object C:
  def c: ZIO[C, Nothing, String] = ZIO.serviceWithZIO[C](_.c)

  def abc: ZIO[C, Nothing, String] = ZIO.serviceWithZIO[C](_.abc)

// Service Layers
object ADefault:
  val layer: ZLayer[Any, Nothing, A] = ZLayer.succeed(apply())

object BDefault:
  val layer: ZLayer[Any, Nothing, B] = ZLayer.succeed(apply())

object CDefault:
  val layer: ZLayer[A & B, Nothing, C] =
    ZLayer {
      for {
        a <- ZIO.service[A]
        b <- ZIO.service[B]
      } yield CDefault(a, b)
    }

/**
  * See https://zio.dev/reference/di/dependency-injection-in-zio for original version.
  */
object ServicePatternApp extends ZIOAppDefault:
  def app: ZIO[A & B & C, Nothing, Boolean] =
    for
      a   <- ZIO.service[A]
      b   <- ZIO.service[B]
      c   <- ZIO.service[C]
      as  <- a.a
      bs  <- b.b
      cs  <- c.c
      abc <- c.abc.debug
      _   <- ZIO.debug(s"a: $as, b: $bs, c: $cs")
      _   <- ZIO.debug(s"abc: $abc")
    yield (as + bs + cs) == abc

  def appx: ZIO[A & B & C, Nothing, Boolean] =
    for
      as  <- A.a
      bs  <- B.b
      cs  <- C.c
      abc <- C.abc
    yield
      val abcs = as + bs + cs
      val isEqual = abcs == abc
      println(s"$abcs == $abc is $isEqual")
      isEqual

  def appLayer: ZLayer[Any, Nothing, A & B & C] =
    ZLayer.make[A & B & C](ADefault.layer, BDefault.layer, CDefault.layer)

  // override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app.provide(appLayer)

  override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = appx.provide(appLayer)