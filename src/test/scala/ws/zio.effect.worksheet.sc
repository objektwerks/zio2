import zio.{ZIO}

// Effects
val success = ZIO.succeed(1)
val failure = ZIO.fail("failure")
val suspend = ZIO.suspend(success)

success
  .map(value => value + 1)
  .map(result => result == 2)

success
  .flatMap(value => ZIO.succeed(value + 1))
  .map(result => result == 2)

ZIO
  .succeed(1)
  .zip(ZIO.succeed(1))
  .map(tuple => tuple._1 + tuple._2 == 2)

