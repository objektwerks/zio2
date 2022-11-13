package objektwerks

import java.time.Instant

import zio.{Console, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}
import zhttp.http.{!!, /, ->, Http, Method, Request, Response}
import zhttp.service.Server

import zio.json.{DecoderOps, EncoderOps}

import Command.given
import Event.given

object HttpServer extends ZIOAppDefault:
  val port = 7272

  val router = Http.collectZIO[Request] {
    case Method.GET -> !! / "now" => ZIO.succeed( Response.text(Instant.now.toString()) )
    case request @ Method.POST -> !! / "greeting" => request.body.asString.map { name => Response.text(s"\nGreetings, $name!\n") }
    case request @ Method.POST -> !! / "command" => request.body.asString.map { json =>
      json.fromJson[Command] match
        case Right(command) => command match
          case Add(x, y) => Response.json( Added( x + y ).toJson )
          case Multiply(x, y) => Response.json( Muliplied( x * y ).toJson )
        case Left(error) => Response.json( Fault( error ).toJson )
    }
  }

  val server =
    for
      _ <- ZIO.log(s"HttpServer running at http://localhost:$port")
      _ <- Server.start(port, router)
    yield()

  override def run: ZIO[Environment & (ZIOAppArgs & Scope ), Any, Any] = server