package objektwerks

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

sealed trait Command

final case class Add(x: Int, y: Int) extends Command
final case class Multiply(x: Int, y: Int) extends Command

object Command:
  given JsonDecoder[Command] = DeriveJsonDecoder.gen[Command]
  given JsonEncoder[Command] = DeriveJsonEncoder.gen[Command]

  given JsonDecoder[Add] = DeriveJsonDecoder.gen[Add]
  given JsonEncoder[Add] = DeriveJsonEncoder.gen[Add]

  given JsonDecoder[Multiply] = DeriveJsonDecoder.gen[Multiply]
  given JsonEncoder[Multiply] = DeriveJsonEncoder.gen[Multiply]