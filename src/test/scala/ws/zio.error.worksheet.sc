import zio.ZIO

// Doesn't work in a ZIO test!
ZIO
  .fail(new Exception("Test Error"))
  .mapError(error => error.getMessage())
  .mapError(error => assert(error == "Test Error"))