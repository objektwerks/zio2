package objektwerks

import zio.{Task, URLayer, ZIO, ZLayer}

trait Service:
  def add(x: Int, y: Int): Task[Int]
  def multiply(x: Int, y: Int): Task[Int]

object Service:
  def add(x: Int, y: Int): ZIO[Service, Nothing, Task[Int]] = ZIO.serviceWith[Service](_.add(x, y))
  def multiply(x: Int, y: Int): ZIO[Service, Nothing, Task[Int]] = ZIO.serviceWith[Service](_.add(x, y))

class ServiceLive extends Service:
  override def add(x: Int, y: Int): Task[Int] = ZIO.attempt(x + y)
  override def multiply(x: Int, y: Int): Task[Int] = ZIO.attempt(x * y)

object ServiceLive:
  val layer: URLayer[Any, Service] = ZLayer.succeed(ServiceLive())