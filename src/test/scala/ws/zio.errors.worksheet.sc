import scala.util.Try

import zio.ZIO

/*
  Errors = checked exceptions
  Defects = runtime exceptions
*/

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
  .mapBoth(
    error => throw new Exception("No value!"),
    value => assert(value == 1)
  )

ZIO
  .attempt( 1 / 0 )
  .foldZIO(
    error => ZIO.fail("Invalid Value"),
    value => ZIO.succeed(value)
  )

ZIO
  .attempt( 1 / 0 )
  .foldCauseZIO(
    cause => ZIO.succeed(cause.defects.toString),
    value => ZIO.succeed(value)
  )

ZIO
  .attempt( 1 / 0 )
  .orDie
