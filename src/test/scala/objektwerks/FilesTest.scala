package objektwerks

import zio.Task
import zio.test._
import zio.test.Assertion.isTrue

object FilesTest extends ZioTest:
  import Files._

  def spec: Spec[Environment, TestFailure[Nothing], TestSuccess] = suite("files.test")(
    test("file") {
      val content: Task[String] = file("build.sbt")
      assert( runtime.unsafeRun( content ).nonEmpty )( isTrue )
    }
  )