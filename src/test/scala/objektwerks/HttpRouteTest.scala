package objektwerks

import zio.ZIO
import zio.http.{!!, /, ->, Body, Http, Request, Response, URL}
import zio.http.model.Method
import zio.test.{assertTrue, ZIOSpecDefault}

object HttpRouteTest extends ZIOSpecDefault:
  val route: Http[Any, Throwable, Request, Response] = Http.collectZIO[Request] {
    case Method.GET -> !! / "get" => ZIO.succeed(Response.text("get"))
    case request @ Method.POST -> !! / "post" => request.body.asString.map { data => Response.text(data) }
  }

  def spec = suite("http route")(
    test("get") {
      val request = Request.get(URL(!! / "get"))
      for
        response <- route.runZIO(request).flatMap(response => response.body.asString)
      yield assertTrue(response == "get")
    },
    test("post") {
      val request = Request.post(Body.fromString("post"), URL(!! / "post"))
      for
        response <- route.runZIO(request).flatMap(response => response.body.asString)
      yield assertTrue(response == "post")
    }
  )