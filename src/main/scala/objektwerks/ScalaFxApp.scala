package objektwerks

import scalafx.Includes._
import scalafx.application.{JFXApp3, Platform}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scalafx.scene.Scene
import scalafx.scene.control.{ListView, TextField}
import scalafx.scene.layout.VBox

import zio.{Chunk, Console, Runtime, Unsafe, ZIO}
import zio.stream.ZStream

/**
  * See alternate implementation by Duran: https://pastebin.com/ErsB0rkc
  * 
  * This dependency: compilerPlugin("com.github.ghik" % "zerowaste" % "0.2.1" cross CrossVersion.full)
  * produces this error: discarded expression with non-Unit value
  * 
  * With standard ZIO code this is a good thing. With ScalaFx event listeners it's an annoyance.
  * 
  * The current solution is employ this approach: ```val _ = ZIO``` code here! See 2 examples below.
  */
object ScalaFxApp extends JFXApp3:
  def shouldBeInFxThread(message: String): Unit = require(Platform.isFxApplicationThread, message)
  def shouldNotBeInFxThread(message: String): Unit = require(!Platform.isFxApplicationThread, message)

  override def start(): Unit =
    val textField = new TextField()
    val listView = new ListView[String]()

    val zioTextFieldChangeListener: ZIO[Any, Any, Unit] =
      ZStream
        .async { emitter =>
          // val _ removes this error: discarded expression with non-Unit value
          val _ = textField.text.onChange { (_, _, newValue) =>
            shouldBeInFxThread("textField.onChange: Error! Not in fx thread!")
            listView.items.value += newValue
            emitter( ZIO.succeed( Chunk(newValue) ) )
          }
        }
        .foreach( Console.printLine(_) )

    stage = new JFXApp3.PrimaryStage:
      scene = new Scene:
        content = new VBox(textField, listView)

    stage.onShown = _ =>
      // val _ removes this error: discarded expression with non-Unit value
      val _ = Future {
        shouldNotBeInFxThread("stage.onShown: Error! In fx thread!")
        Unsafe.unsafe { implicit unsafe =>
          Runtime
            .default
            .unsafe
            .run(zioTextFieldChangeListener)
            .exitCode
        }
      }