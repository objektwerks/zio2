package objektwerks

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

sealed trait Command

final case class Triple(value: Int) extends Command

object Command:
  given decoder: JsonDecoder[Command] = DeriveJsonDecoder.gen[Command]
  given encoder: JsonEncoder[Command] = DeriveJsonEncoder.gen[Command]