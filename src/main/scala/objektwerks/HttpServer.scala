package objektwerks

import java.time.Instant

import zio.*
import zhttp.http.*
import zhttp.http.Http
import zhttp.service.Server

object HttpServer extends ZIOAppDefault:
  val port = 7272

  val router = Http.collectZIO[Request] {
    case Method.GET -> !! / "now" => ZIO.succeed( Response.text(Instant.now.toString()) )
  }

  val server = for {
    _ <- Console.printLine(s"Http server running at http://localhost:$port")
    _ <- Server.start(port, router)
  } yield()

  override def run = server