package objektwerks

import zio.{Console, Runtime, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

case class Todo(id: Int = 0, task: String)

case class Store():
  import com.typesafe.config.{Config, ConfigFactory}
  import io.getquill.*

  val conf = ConfigFactory.load("quill.conf")
  val ctx = new H2JdbcContext(SnakeCase, conf.getConfig("quill.ctx"))
  import ctx._

  inline def addTodo(todo: Todo): Int =
    run( 
      query[Todo]
        .insert( lift(todo => (todo.id, todo.task)) )
        .returningGenerated(_.id)
    )

  inline def updateTodo(todo: Todo): Long =
    run(
      query[Todo]
        .filter(_.id == lift(todo.id))
        .update( lift(todo => (todo.id, todo.task)) )
    )

  inline def listTodos: List[Todo] = run( query[Todo] )

object QuillApp extends ZIOAppDefault:
  def app: ZIO[Any, Exception, Unit] =
    for
      _  <- Console.printLine("TODO!")
    yield ()

  def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app