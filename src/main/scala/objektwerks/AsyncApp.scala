package objektwerks

import zio.{durationInt, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

object AsyncApp extends ZIOAppDefault:

  override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = ???