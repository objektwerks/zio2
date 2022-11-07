package objektwerks

sealed trait Event

final case class Doubled(value: Double) extends Event