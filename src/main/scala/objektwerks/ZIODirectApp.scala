package objektwerks

import zio.{IO, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}
import zio.direct.*

final case class DoubleError()
final case class TripleError()

def double(i: Int): IO[DoubleError, Int] = ZIO.succeed(i * 2)
def triple(i: Int): IO[TripleError, Int] = ZIO.succeed(i * 3)

object ZIODirect extends ZIOAppDefault:
  val sum: IO[DoubleError | TripleError, Int] =
    defer {
      double(1).run + triple(2).run
    }

  def app: ZIO[Any, DoubleError | TripleError, Int] =
    for
      result <- sum
    yield result

  def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app.debug("sum")