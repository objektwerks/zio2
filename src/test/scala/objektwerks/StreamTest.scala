package objektwerks

import zio.Chunk
import zio.stream.{ZSink, ZStream}
import zio.test.{assertTrue, ZIOSpecDefault}

object StreamTest extends ZIOSpecDefault:
  def spec = suite("streams")(
    test("map") {
      ZStream(1, 2, 3)
      .map( i => i * 2 )
      .run(ZSink.sum)
      .map( result => assertTrue( result == 12 ) )
    },
    test("filter") {
      ZStream(1, 2, 3)
      .filter( i => i % 2 == 0)
      .run(ZSink.sum)
      .map( result => assertTrue( result == 2 ) )
    },
    test("fold") {
      ZStream(1, 2, 3)
      .runFold(0)(_ + _)
      .map( result => assertTrue( result == 6 ) )
    },
    test("sum") {
      ZStream(1, 2, 3)
      .run(ZSink.sum)
      .map( result => assertTrue( result == 6 ) )
    },
    test("chunk") {
      ZStream
      .fromChunks( Chunk(1, 2, 3), Chunk(4, 5, 6), Chunk(7, 8, 9) )
      .mapChunks( chunk => chunk.filter( i => i % 2 != 0) )
      .run(ZSink.sum)
      .map( result => assertTrue( result == 25 ) )
    }
  )