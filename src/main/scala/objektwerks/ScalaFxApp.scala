package objektwerks

import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.control.{ListView, TextField}

import zio.{Chunk, Console, Runtime, Unsafe, ZIO}
import zio.stream.ZStream

object ScalaFxApp extends JFXApp3:
  override def start(): Unit =
    val sourceTextField = new TextField()
    val targetListView = new ListView[String]()

    val zioStream = ZStream.async[Any, Any, String] { emitter =>
      sourceTextField.text.onChange { (_, _, newValue) =>
        emitter( ZIO.succeed( Chunk(newValue) ) )
      }
    }

    val zioApp =
      for
        text <- zioStream
      yield targetListView.getItems().add(text)

    stage = new JFXApp3.PrimaryStage {
      scene = new Scene {
        content = sourceTextField
      }
    }

    stage.onShown.onChange { (_, _, _) =>
      Unsafe.unsafe { implicit unsafe =>
        Runtime.default.unsafe.run(zioApp).getOrThrowFiberFailure()
      }
    }