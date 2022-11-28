package objektwerks

import zio.ZIOAppDefault

import zio.{Console, Runtime, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}
import com.typesafe.config.Config

case class Todo(id: Int = 0, task: String)

class Store(conf: Config) {
  implicit val ctx = new H2JdbcContext(SnakeCase, conf.getConfig("quill.ctx"))
  import ctx._

  def addTodo(todo: Todo): Int = run( query[Todo].insert(lift(todo)).returningGenerated(_.id) )

  def updateTodo(todo: Todo): Unit = {
    run( query[Todo].filter(_.id == lift(todo.id)).update(lift(todo)) )
    ()
  }

  def listTodos(): Seq[Todo] = run( query[Todo] )
}

object QuillApp extends ZIOAppDefault:
  def app: ZIO[Any, Exception, Unit] =
    for
      _  <- Console.printLine("TODO!")
    yield ()

  def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app