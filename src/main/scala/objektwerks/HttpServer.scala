package objektwerks

import java.time.Instant

import zio._
import zhttp.http.*
import zhttp.http.Http
import zhttp.service.Server

object HttpServer extends ZIOAppDefault:
  val port = 7272

  val router: Http[Any, Nothing, Request, Response] = Http.collect[Request] {
    case Method.GET -> !! / "now" => Response.text(Instant.now.toString())
  }

  val server = for {
    _ <- Console.printLine(s"Http server started at http://localhost:$port")
    _ <- Server.start(port, router)
  } yield()

  override def run = server