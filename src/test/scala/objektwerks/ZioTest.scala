package objektwerks

import zio.{Runtime, ZEnv}
import zio.test._

trait ZioTest extends DefaultRunnableSpec:
  protected val runtime: Runtime[ZEnv] = Runtime.default