package objektwerks

import zio.ZIO
import zio.http.{!!, /, ->, Http, Request, Response, URL}
import zio.http.model.{Method, Status}
import zio.test.{assertTrue, ZIOSpecDefault}
import zio.test.Assertion.*

object HttpTest extends ZIOSpecDefault:
  val route: Http[Any, Nothing, Request, Response] = Http.collectZIO[Request] {
    case Method.GET -> !! / "test" => ZIO.succeed( Response.text("test") )
  }

  def spec = suite("http")(
    test("route") {
      val path = !! / "test"
      val request = Request.get(url = URL(path))
      for {
        expectedBody <- route(request).flatMap(_.body.asString)
      } yield assertTrue(expectedBody == "test")
    }
  )