package objektwerks

import java.time.Instant

import zio._
import zhttp.http.*
import zhttp.http.Http
import zhttp.service.Server

object HttpApp extends ZIOAppDefault:  
  val router: Http[Any, Nothing, Request, Response] = Http.collect[Request] {
    case Method.GET -> !! / "now" => Response.text(Instant.now.toString())
  }

  override def run = Server.start(7272, router)