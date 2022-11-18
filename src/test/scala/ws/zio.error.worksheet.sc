import scala.util.Try

import zio.ZIO

// Doesn't work in a ZIO test!
ZIO
  .fail(new Exception("Test Error"))
  .mapError(error => error.getMessage())
  .mapError(error => assert(error == "Test Error"))

ZIO
  .fromTry( Try(1 / 0))
  .catchAll(_ => ZIO.succeed(0))