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
      get      <- cache.get(1) // Lookup caches, or puts, 1 if it doesn't exist in the cache!
      _        <- ZIO.debug(s"cache.get(1) => $get")
      contains <- cache.contains(1)
      _        <- ZIO.debug(s"cache.contains(1) => $contains")
    yield () // Make a cache in the layer that constructs your service.

  def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app