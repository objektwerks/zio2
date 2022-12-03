package objektwerks

import zio.ZIOAppDefault

import zio.{Console, Duration, Runtime, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}
import zio.cache.{Cache, Lookup}

object CacheApp extends ZIOAppDefault:
  def app =
    for
      cache  <- Cache
                  .make(capacity = 100,
                        timeToLive = Duration.Infinity,
                        lookup = Lookup( (n: Int) => ZIO.debug(s"Computing $n").as(n) ) )
      first  <- cache.get(1)
      second <- cache.get(1)
    yield ()

  def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app