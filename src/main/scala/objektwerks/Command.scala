package objektwerks

sealed trait Command

final case class Double(value: Double) extends Command