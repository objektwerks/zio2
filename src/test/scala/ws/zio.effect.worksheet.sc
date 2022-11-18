import zio.{ZIO}

// Effects
val success = ZIO.succeed(1)
val failure = ZIO.fail("failure")
val suspend = ZIO.suspend(success)