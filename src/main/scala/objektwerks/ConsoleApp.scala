package objektwerks

import zio.Console.{printLine, readLine}
import zio.{Runtime, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}
import zio.logging.{LogFormat, console}

object ConsoleApp extends ZIOAppDefault:
  override val bootstrap: ZLayer[ZIOAppArgs, Any, Environment] = Runtime.removeDefaultLoggers >>> console(LogFormat.default)

  def run: ZIO[Environment & (ZIOAppArgs & Scope ), Any, Any] =
    for
      _    <- ZIO.log("Greetings! What is your name?")
      _    <- printLine("*** Greetings! What is your name?")
      name <- readLine
      _    <- ZIO.log(s"Welcome, ${name}!")
      _    <- printLine(s"*** Welcome, ${name}!")
    yield ()