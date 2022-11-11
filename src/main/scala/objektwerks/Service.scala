package objektwerks

import zio.{Task, ZIO}

trait Service:
  def add(x: Int, y: Int): Task[Int]
  def multiply(x: Int, y: Int): Task[Int]

object Service:
  def add(x: Int, y: Int): ZIO[Service, Nothing, Task[Int]] = ZIO.serviceWith[Service](_.add(x, y))
  def multiply(x: Int, y: Int): ZIO[Service, Nothing, Task[Int]] = ZIO.serviceWith[Service](_.add(x, y))

class ServiceLive extends Service:
  override def add(x: Int, y: Int): Task[Int] = ZIO.attempt(x + y)
  override def multiply(x: Int, y: Int): Task[Int] = ZIO.attempt(x * y)