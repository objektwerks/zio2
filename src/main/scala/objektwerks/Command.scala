package objektwerks

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

sealed trait Command

final case class Add(x: Int, y: Int) extends Command:
  def add: Int = x + y

object Command:
  given decoder: JsonDecoder[Command] = DeriveJsonDecoder.gen[Command]
  given encoder: JsonEncoder[Command] = DeriveJsonEncoder.gen[Command]