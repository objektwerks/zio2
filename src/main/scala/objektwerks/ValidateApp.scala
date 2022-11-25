package objektwerks

import zio.{Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

object ValidateApp extends ZIOAppDefault:
  def app(values: List[Int], limit: Int): ZIO[Any, List[String], List[Int]] =
    ZIO.validate(values) { i =>
      if (i < limit) then ZIO.succeed(i)
      else ZIO.fail(s"$i >= $limit")
    }

  override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] =
    app(List.range(1, 8), 4)