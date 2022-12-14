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

object ScalaFxApp extends JFXApp3:
  override def start(): Unit =
    val textField = new TextField()
    val listView = new ListView[String]()
    val vbox = new VBox(textField, listView)

    textField.text.onChange { (_, _, newValue) =>
      listView.getItems().add(newValue)
    }

    stage = new JFXApp3.PrimaryStage:
      scene = new Scene:
        content = vbox

    val zioApp: ZIO[Any, Any, Unit] =
      for
        fiber <- ZStream
                   .async { emitter =>
                     textField.text.onChange { (_, _, newValue) =>
                       emitter( ZIO.succeed( Chunk(newValue) ) )
                     }
                   }
                   .foreach( Console.printLine(_) ).fork
        _     <-  fiber.join
      yield ()

    stage.onShown = _ =>
      Future {
        Unsafe.unsafe { implicit unsafe =>
          Runtime
            .default
            .unsafe
            .run(zioApp)
            .getOrThrowFiberFailure()
        }
      }