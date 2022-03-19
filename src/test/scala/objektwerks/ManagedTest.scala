package objektwerks

import scala.io.BufferedSource

import zio.Task
import zio.managed._
import zio.test._
import zio.test.Assertion.isTrue

object ManagedTest extends ZioTest:
  import Resources._

  def spec: Spec[Environment, TestFailure[Nothing], TestSuccess] = suite("managed.test")(
    test("managed") {
      val managed: ZManaged[Any, Throwable, BufferedSource] = Managed
        .acquireReleaseWith(resource("build.sbt"))(close(_).ignore)
      val task: Task[String] = managed.use { source => stringify(source) }
      assert( runtime.unsafeRun( task ).nonEmpty )( isTrue )
    }
  )