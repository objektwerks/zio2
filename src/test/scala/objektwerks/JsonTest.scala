package objektwerks

import zio.json.{ DecoderOps, EncoderOps }
import zio.test.{ assertTrue, ZIOSpecDefault }

import Command.given
import Event.given

object JsonTest extends ZIOSpecDefault:
  def spec = suite("json")(
    test("command") {
      val command: Command = Add(1, 1)
      val commandJson = command.toJson
      assertTrue( command == commandJson.fromJson[Command].getOrElse( Add(0, 0) ) )
    },
    test("event") {
      val event: Event = Added(2)
      val eventJson = event.toJson
      assertTrue( event == eventJson.fromJson[Event].getOrElse( Added(0) ) )
    },
    test("sub command") {
      val multiply = Multiply(1, 2)
      val multiplyJson = multiply.toJson
      assertTrue( multiply == multiplyJson.fromJson[Multiply].getOrElse( Multiply(0, 0) ) )
    },
    test("sub event") {
      val multiplied = Muliplied(2)
      val multipliedJson = multiplied.toJson
      assertTrue( multiplied == multipliedJson.fromJson[Muliplied].getOrElse( Muliplied(0) ) )
    }
  )