package objektwerks

import zio.{Console, Duration, Runtime, Scope, UIO, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}
import zio.cache.{Cache, Lookup}

case class CacheService(cache: Cache[Int, Nothing, Int]):
  def get(n: Int): UIO[Int] = cache.get(n)
  def contains(n: Int): UIO[Boolean] = cache.contains(n)

object CacheService:
  val layer: ZLayer[Any, Nothing, CacheService] =
    ZLayer {
      for
        cache <- Cache.make(capacity = 10,
                            timeToLive = Duration.Infinity,
                            lookup = Lookup( (n: Int) => ZIO.debug(s"lookup cached => $n").as(n) ) )
      yield CacheService(cache)
    }

object CacheApp extends ZIOAppDefault:
  def app: ZIO[CacheService, Nothing, Unit] =
    for
      cache    <- ZIO.service[CacheService]
      get      <- cache.get(1) // Lookup caches, or puts, 1 if it doesn't exist in the cache!
      _        <- ZIO.debug(s"cache.get(1) => $get")
      contains <- cache.contains(1)
      _        <- ZIO.debug(s"cache.contains(1) => $contains")
    yield () // Make a cache in the layer that constructs your service.

  def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app.provide(CacheService.layer)