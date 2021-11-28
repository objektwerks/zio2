package objektwerks

import zio.ZIOAppDefault
import zio.Console._

object ConsoleApp extends ZIOAppDefault:
  def run =
    for {
      _    <- printLine("Greetings! What is your name?")
      name <- readLine
      _    <- printLine(s"Welcome, ${name}!")
    } yield ()