package objektwerks

import zio.Console.*
import zio.ZIOAppDefault

object ConsoleApp extends ZIOAppDefault:
  def run =
    for {
      _    <- printLine("Greetings! What is your name?")
      name <- readLine
      _    <- printLine(s"Welcome, ${name}!")
    } yield ()