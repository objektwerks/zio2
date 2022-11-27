package objektwerks

import zio.ZIOAppDefault

import zio.{Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

object ServicePatternApp extends ZIOAppDefault:
  override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = ???