package objektwerks

import zio.json.*

final case class Event(name: String)

object Event:
  given JsonDecoder[Event] = DeriveJsonDecoder.gen[Event]
  given JsonEncoder[Event] = DeriveJsonEncoder.gen[Event]