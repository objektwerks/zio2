package objektwerks

import zio.Chunk
import zio.Console.printLine
import zio.stream.{ZChannel, ZPipeline, ZSink, ZStream}
import zio.test.{assertTrue, ZIOSpecDefault}

object StreamTest extends ZIOSpecDefault:
  def spec = suite("streams")(
    test("map") {
      ZStream(1, 2, 3)
        .map(i => i * 2)
        .run(ZSink.sum)
        .map(result => assertTrue(result == 12))
    },
    test("filter") {
      ZStream(1, 2, 3)
        .filter(i => i % 2 == 0)
        .run(ZSink.sum)
        .map(result => assertTrue(result == 2))
    },
    test("fold") {
      ZStream(1, 2, 3)
        .runFold(0)(_ + _)
        .map(result => assertTrue(result == 6))
    },
    test("sum") {
      ZStream(1, 2, 3)
        .run(ZSink.sum)
        .map(result => assertTrue(result == 6))
    },
    test("chunk") {
      ZStream
        .fromChunks(Chunk(1, 2, 3), Chunk(4, 5, 6), Chunk(7, 8, 9))
        .mapChunks(chunk => chunk.filter(i => i % 2 != 0))
        .run(ZSink.sum)
        .map(result => assertTrue(result == 25))
    },
    test("pipeline") {
      val strings = ZStream("1", "2", "3", "4", "5", "6")

      val toIntPipeLine = ZPipeline.map[String, Int](string => string.toInt)
      val filterOddPipeLine = ZPipeline.filter[Int](int => int % 2 != 0)
      val pipeline = toIntPipeLine >>> filterOddPipeLine

      strings
        .via(pipeline)
        .run((ZSink.sum))
        .map(result => assertTrue(result == 9))
    },
    test("error") {
      val invalidStream = ZStream(1, 2, 3) ++ ZStream.fail("a", "b", "c") ++ ZStream(4, 5, 6)
      val validStream = ZStream(1, 2, 3, 4, 5, 6)
      invalidStream  // ZStream(1, 2, 3) is included below; hence the sum of 27!
        .orElse(validStream) // .catchAllCause(_ => validStream) produces same result.
        .tap(i => printLine(i))
        .run((ZSink.sum))
        .tap(i => printLine(i)) // ZStream(1, 2, 3) + ZStream(1, 2, 3, 4, 5, 6) = 27
        .map(result => assertTrue(result == 27))
    }
  )