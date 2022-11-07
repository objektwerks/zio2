package objektwerks

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

sealed trait Command

final case class Triple(value: Int) extends Command

object Triple:
  given decoder: JsonDecoder[Triple] = DeriveJsonDecoder.gen[Triple]
  given encoder: JsonEncoder[Triple] = DeriveJsonEncoder.gen[Triple]