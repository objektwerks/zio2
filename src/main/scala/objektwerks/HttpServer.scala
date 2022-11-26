package objektwerks

import java.nio.file.Path
import java.time.Instant

import zio.{Runtime, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}
import zio.http.{!!, /, ->, Http, Request, Response, Server, ServerConfig}
import zio.http.model.Method
import zio.json.{DecoderOps, EncoderOps}
import zio.logging.{LogFormat, file}

import Command.given
import Event.given

object HttpServer extends ZIOAppDefault:
  val port = 7272
  val config = ServerConfig.default.port(port) // ZIO.log(s"HttpServer running at http://localhost:$port")

  override val bootstrap: ZLayer[ZIOAppArgs, Any, Environment] =
    Runtime.removeDefaultLoggers >>> file(Path.of("./target/http-server.log"))

  def router: Http[Any, Throwable, Request, Response] = Http.collectZIO[Request] {
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

  override def run: ZIO[Environment & (ZIOAppArgs & Scope ), Any, Any] =
    Server
      .serve(router)
      .provide(ServerConfig.live(config), Server.live)