package objektwerks

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

sealed trait Event

case class Added(z: Int) extends Event
case class Muliplied(z: Int) extends Event
case class Fault(cause: String) extends Event

object Event:
  given JsonDecoder[Event] = DeriveJsonDecoder.gen[Event]
  given JsonEncoder[Event] = DeriveJsonEncoder.gen[Event]

  given JsonDecoder[Added] = DeriveJsonDecoder.gen[Added]
  given JsonEncoder[Added] = DeriveJsonEncoder.gen[Added]

  given JsonDecoder[Muliplied] = DeriveJsonDecoder.gen[Muliplied]
  given JsonEncoder[Muliplied] = DeriveJsonEncoder.gen[Muliplied]

  given JsonDecoder[Fault] = DeriveJsonDecoder.gen[Fault]
  given JsonEncoder[Fault] = DeriveJsonEncoder.gen[Fault]