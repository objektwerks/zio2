package objektwerks

import zio.ZIOAppDefault

import zio.{Console, Duration, Runtime, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}
import zio.cache.{Cache, Lookup}

object CacheApp extends ZIOAppDefault:
  def app =
    for
      cache    <- Cache.make(capacity = 10,
                             timeToLive = Duration.Infinity,
                             lookup = Lookup( (n: Int) => ZIO.debug(s"lookup cached => $n").as(n) ) )
      put      <- cache.get(1) // lookup caches, or puts, 1 if it doesn't exist in the cache!
      _        <- ZIO.debug(s"cache.get(1) => $put")
      contains <- cache.contains(1)
      _        <- ZIO.debug(s"cache.contains(1) => $contains")
    yield ()

  def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app