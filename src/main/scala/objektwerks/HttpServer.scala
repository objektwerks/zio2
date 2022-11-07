package objektwerks

import java.time.Instant

import zio.{ Console, ZIO, ZIOAppDefault }
import zhttp.http.{ !!, /, ->, Http, Method, Request, Response }
import zhttp.service.Server

object HttpServer extends ZIOAppDefault:
  val port = 7272

  val router = Http.collectZIO[Request] {
    case Method.GET -> !! / "now" => ZIO.succeed( Response.text(Instant.now.toString()) )
    case request @ Method.POST -> !! / "greeting" => ZIO.succeed( Response.text(s"Greetings, ${request.body.asString.map(s => s)}!") )
  }

  val server = for {
    _ <- Console.printLine(s"Http server running at http://localhost:$port")
    _ <- Server.start(port, router)
  } yield()

  override def run = server