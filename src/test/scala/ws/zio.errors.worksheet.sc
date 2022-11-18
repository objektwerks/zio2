import scala.util.Try

import zio.ZIO

// ZIO fail doesn't work in a ZIO test.
ZIO
  .fail(new Exception("Test Error"))
  .mapError(error => error.getMessage())
  .mapError(error => assert(error == "Test Error"))

ZIO
  .fromTry( Try(1 / 0) )
  .catchAll(_ => ZIO.succeed(0))
  .map(value => assert(value == 0))

ZIO
  .fromEither( Right(1) )
  .mapAttempt(value => assert(value == 1))

ZIO
  .fromOption( Some(1) )
  .mapBoth(error => throw new Exception("No value!"), value => assert(value == 1))