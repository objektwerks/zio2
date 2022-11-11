package objektwerks

import zio.{Task}

trait Service:
  def add(x: Int, y: Int): Task[Int]
  def multiply(x: Int, y: Int): Task[Int]

