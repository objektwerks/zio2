package objektwerks

import zio.ZIOAppDefault

import zio.{Console, Duration, Runtime, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}
import zio.cache.{Cache, Lookup}

object CacheApp extends ZIOAppDefault:
  def app =
    for
      cache  <- Cache.make(capacity = 100,
                           timeToLive = Duration.Infinity,
                           lookup = Lookup( (n: Int) => ZIO.debug(s"lookup $n").as(n) ) )
      first  <- cache.get(1)
      _      <- ZIO.debug(s"first get(1): $first")
      second <- cache.get(1)
      _      <- ZIO.debug(s"second get(1): $second")
    yield ()

  def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app