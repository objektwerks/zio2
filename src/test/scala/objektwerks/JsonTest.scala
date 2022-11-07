package objektwerks

import zio.json.*
import zio.test.*

object JsonTest extends ZIOSpecDefault:
  def spec = suite("json")(
    test("command") {
      import Command.given

      val command: Command = Triple(1)
      val commandJson = command.toJson
      assertTrue( command == commandJson.fromJson[Command].getOrElse( Triple(-1) ) )
    },
    test("event") {
      import Event.given

      val event: Event = Tripled(3)
      val eventJson = event.toJson
      assertTrue( event == eventJson.fromJson[Event].getOrElse( Tripled(-1) ) )
    }
  )