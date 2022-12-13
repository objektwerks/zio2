package objektwerks

import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.control.{ListView, TextField}
import scalafx.scene.layout.VBox

import zio.{Chunk, Console, Runtime, Unsafe, ZIO}
import zio.stream.ZStream
import scala.annotation.newMain

object ScalaFxApp extends JFXApp3:
  override def start(): Unit =
    val textField = new TextField()
    val listView = new ListView[String]()
    val vbox = new VBox(textField, listView)

    val zioStream = ZStream.async[Any, Any, String] { emitter =>
      textField.text.onChange { (_, _, newValue) =>
        emitter( ZIO.succeed( Chunk(newValue) ) )
      }
    }

    val zioApp =
      ZIO.succeed {
        for
          text <- zioStream
        yield listView.getItems().add(text)
      }.debug

    stage = new JFXApp3.PrimaryStage {
      scene = new Scene {
        content = vbox
      }
    }

    stage.onShown.onChange { (_, _, _) =>
      Unsafe.unsafe { implicit unsafe =>
        Runtime.default.unsafe.run(zioApp).getOrThrowFiberFailure()
      }
    }