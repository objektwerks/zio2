package objektwerks

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

sealed trait Event

final case class Tripled(value: Int) extends Event

object Tripled:
  given decoder: JsonDecoder[Tripled] = DeriveJsonDecoder.gen[Tripled]
  given encoder: JsonEncoder[Tripled] = DeriveJsonEncoder.gen[Tripled]