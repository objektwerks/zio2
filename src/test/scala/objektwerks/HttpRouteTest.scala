package objektwerks

import zio.ZIO
import zio.http.{!!, /, ->, Http, Request, Response, URL}
import zio.http.model.Method
import zio.test.{assertTrue, ZIOSpecDefault}

object HttpRouteTest extends ZIOSpecDefault:
  val route: Http[Any, Nothing, Request, Response] = Http.collectZIO[Request] {
    case Method.GET -> !! / "test" => ZIO.succeed(Response.text("test"))
  }

  def spec = suite("http")(
    test("route") {
      val request = Request.get(URL(!! / "test"))
      for
        response <- route(request).flatMap(response => response.body.asString)
      yield assertTrue(response == "test")
    }
  )