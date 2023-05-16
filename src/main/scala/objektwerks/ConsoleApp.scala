package objektwerks

import java.io.IOException
import java.nio.file.Path

import zio.{Console, Runtime, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}
import zio.logging.file

object ConsoleApp extends ZIOAppDefault:
  override val bootstrap: ZLayer[ZIOAppArgs, Any, Environment] =
    Runtime.removeDefaultLoggers >>> file(Path.of("./target/console.log"))

  def app: ZIO[Any, IOException, Unit] =
    for
      _    <- ZIO.log("Greetings! What is your name?")
      _    <- Console.printLine("*** Greetings! What is your name?")
      name <- Console.readLine
      _    <- ZIO.log(s"Welcome, ${name}!")
      _    <- Console.printLine(s"*** Welcome, ${name}!")
    yield ()

  def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app