package objektwerks

import zio.{Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

object ValidateApp extends ZIOAppDefault:
  def app(values: List[Int], limit: Int): ZIO[Any, Nothing, (Iterable[String], Iterable[Int])] =
    ZIO.partition(values) { i =>
      if (i <= limit) then ZIO.succeed(i).debug
      else ZIO.fail(s"$i > $limit").debug
    }

  override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] =
    app(List.range(1, 5), 2)
      .map { validations =>
        val (errors, successes) = validations
        assert(errors.size == 2 && successes.size == 2)
      }