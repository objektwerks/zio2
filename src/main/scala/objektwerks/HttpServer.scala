package objektwerks

import zio.*
import zio.http.*
import zio.json.*

import Command.given
import Event.given

object HttpServer extends ZIOAppDefault:
  val routes: Routes[Any, Response] = Routes(
    Method.POST / "command" -> handler: (request: Request) =>
      for
        body    <- request.body.asString.orDie
        command <- ZIO.fromEither( body.fromJson[Command] )
        event   = Event( command.name ).toJson
      yield Response.json(event)
  ).handleError( _ match
    case error: String => Response.json( Event(s"Invalid json: $error").toJson )
  )

  def run = Server
    .serve(routes)
    .provide(Server.defaultWithPort(6060))