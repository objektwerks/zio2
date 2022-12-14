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
  */
object ScalaFxApp extends JFXApp3:
  val shouldBeInFxThread = (message: String) => require(Platform.isFxApplicationThread, message)
  val shouldNotBeInFxThread = (message: String) => require(!Platform.isFxApplicationThread, message)

  override def start(): Unit =
    val textField = new TextField()
    val listView = new ListView[String]()
    val vbox = new VBox(textField, listView)

    val zioTextFieldChangeListener: ZIO[Any, Any, Unit] =
      ZStream
        .async { emitter =>
          textField.text.onChange { (_, _, newValue) =>
            shouldBeInFxThread("textField.onChange: Error! Not in fx thread!")
            listView.getItems().add(newValue)
            emitter( ZIO.succeed( Chunk(newValue) ) )
          }
        }
        .foreach( Console.printLine(_) )

    stage = new JFXApp3.PrimaryStage:
      scene = new Scene:
        content = vbox

    stage.onShown = _ =>
      Future {
        shouldNotBeInFxThread("stage.onShown: Error! In fx thread!")
        Unsafe.unsafe { implicit unsafe =>
          Runtime
            .default
            .unsafe
            .run(zioTextFieldChangeListener)
            .exitCode
        }
      }