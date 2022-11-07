package objektwerks

import zio.json.{ DecoderOps, EncoderOps }
import zio.test.{ assertTrue, ZIOSpecDefault }

object JsonTest extends ZIOSpecDefault:
  def spec = suite("json")(
    test("command") {
      import Command.*

      val command: Command = Add(1, 1)
      val commandJson = command.toJson
      assertTrue( command == commandJson.fromJson[Command].getOrElse( Add(0, 0) ) )
    },
    test("event") {
      import Event.*

      val event: Event = Added(2)
      val eventJson = event.toJson
      assertTrue( event == eventJson.fromJson[Event].getOrElse( Added(-1) ) )
    }
  )