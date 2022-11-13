package objektwerks

import zio.Console.{printLine, readLine}
import zio.{Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

object ConsoleApp extends ZIOAppDefault:
  def run: ZIO[Environment & (ZIOAppArgs & Scope ), Any, Any] =
    for
      _    <- printLine("Greetings! What is your name?")
      name <- readLine
      _    <- printLine(s"Welcome, ${name}!")
    yield ()