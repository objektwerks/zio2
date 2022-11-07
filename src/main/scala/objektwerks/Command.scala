package objektwerks

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

sealed trait Command

final case class Add(x: Int, y: Int) extends Command
final case class Multiply(x: Int, y: Int) extends Command

object Command:
  given decoder: JsonDecoder[Command] = DeriveJsonDecoder.gen[Command]
  given encoder: JsonEncoder[Command] = DeriveJsonEncoder.gen[Command]