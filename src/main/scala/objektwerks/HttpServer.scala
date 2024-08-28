package objektwerks

import java.nio.file.Path
import java.time.Instant

import zio.{Runtime, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}
import zio.http.{Charsets, handler, Handler, Method, Request, Response, Routes, Server}
import zio.json.{DecoderOps, EncoderOps}

import Command.given
import Event.given

object HttpServer extends ZIOAppDefault:
  val routes: Routes[String, Response] = Routes(
    Method.POST / "command" -> handler: (request: Request) => request.body.asString.map { json =>
      json.fromJson[Command] match
        case Right(command) => command match
          case Add(x, y) => Response.json( Added( x + y ).toJson )
          case Multiply(x, y) => Response.json( Muliplied( x * y ).toJson )
        case Left(error) => Response.json( Fault( error ).toJson )
    }
  )

  override def run: ZIO[Environment & (ZIOAppArgs & Scope ), Any, Any] =
    for
      args   <- getArgs
      port   =  args.headOption.getOrElse("7272").toInt
      config =  Server.Config.default.port(port)
      _      <- ZIO.log(s"HttpServer running at http://localhost:$port")
      server <- Server
                  .serve(routes.withDefaultErrorResponse)
                  .provide(ServerConfig.live(config), Server.live)
                  .exitCode
    yield server