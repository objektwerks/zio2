package objektwerks

import scalafx.Includes._
import scalafx.application.{JFXApp3, Platform}
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

    val stream = ZStream[String]()

    textField.text.onChange { (_, _, newValue) =>
      listView.getItems().add(newValue)
      stream.concat( ZStream( newValue ) )
    }

    val zioApp =
      ZIO.succeed {
        for
          text <- stream
        yield Console.printLine(text)
      }

    stage = new JFXApp3.PrimaryStage {
      scene = new Scene {
        content = vbox
      }
    }

    stage.onShown.onChange { (_, _, _) =>
      Unsafe.unsafe { implicit unsafe =>
        Runtime.default.unsafe
          .run( zioApp )
          .debug
      }
    }