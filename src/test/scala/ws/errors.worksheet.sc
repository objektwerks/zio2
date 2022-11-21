import scala.util.Try

import zio.ZIO

/*
  Errors = checked exceptions
  Defects = runtime exceptions
*/

// ZIO fail doesn't seem to work in a ZIO test.
ZIO
  .fail(new Exception("Test Error"))
  .mapError(error => error.getMessage())
  .mapError(error => assert(error == "Test Error"))