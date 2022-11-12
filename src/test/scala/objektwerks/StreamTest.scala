package objektwerks

import zio.test.ZIOSpecDefault

import zio.Chunk
import zio.stream.ZStream
import zio.test.{assertTrue, ZIOSpecDefault}
import zio.stream.ZSink

object StreamTest extends ZIOSpecDefault:
  def spec = suite("streams")(
    test("fold") {
      ZStream(1, 2, 3).runFold(0)(_ + _).map( result => assertTrue( result == 6 ) )
    },
    test("sum") {
      ZStream(1, 2, 3).run(ZSink.sum).map( result => assertTrue( result == 6 ) )
    }
  )