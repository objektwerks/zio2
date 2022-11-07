package objektwerks

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

sealed trait Command

final case class Triple(value: Int) extends Command

object Triple:
  implicit val decoder: JsonDecoder[Triple] = DeriveJsonDecoder.gen[Triple]
  implicit val encoder: JsonEncoder[Triple] = DeriveJsonEncoder.gen[Triple]