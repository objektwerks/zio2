package objektwerks

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

sealed trait Event

final case class Tripled(value: Int) extends Event

object Event:
  given decoder: JsonDecoder[Event] = DeriveJsonDecoder.gen[Event]
  given encoder: JsonEncoder[Event] = DeriveJsonEncoder.gen[Event]