package objektwerks

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

sealed trait Event

case class Added(z: Int) extends Event
case class Muliplied(z: Int) extends Event
case class Fault(cause: String) extends Event

object Event:
  given decoder: JsonDecoder[Event] = DeriveJsonDecoder.gen[Event]
  given encoder: JsonEncoder[Event] = DeriveJsonEncoder.gen[Event]