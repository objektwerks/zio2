package objektwerks

import zio.json.*
import zio.test.*

object JsonTest extends ZIOSpecDefault:
  def spec = suite("json")(
    test("command") {
      import Triple.given

      val command = Triple(1)
      val commandJson = command.toJson
      assertTrue( command == commandJson.fromJson[Command] )
    },
    test("event") {

    }
  )