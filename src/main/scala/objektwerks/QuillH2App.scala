package objektwerks

import io.getquill.*
import io.getquill.jdbczio.Quill.H2

import java.io.IOException
import java.sql.SQLException

import zio.{Console, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

final case class Todo(id: Int = 0, task: String)

final case class H2Store(quill: H2[SnakeCase]):
  import quill.*

  def addTodo(todo: Todo): ZIO[Any, SQLException, Int] =
    run( 
      query[Todo]
        .insertValue( lift(todo) )
        .returningGenerated(_.id)
    )

  def updateTodo(todo: Todo): ZIO[Any, SQLException, Long] =
    run(
      query[Todo]
        .filter(_.id == lift(todo.id))
        .updateValue( lift(todo) )
    )

  def listTodos: ZIO[Any, SQLException, List[Todo]] = run( query[Todo] )

object H2Store:
  val layer: ZLayer[Any, IOException, H2Store] =
    ZLayer {
      for
        config <- Resources.loadZIOConfig(path = "quill.conf", section = "h2")
      yield H2Store(H2(SnakeCase, H2JdbcContext(SnakeCase, config).dataSource))
    }

object QuillH2App extends ZIOAppDefault:
  def app: ZIO[H2Store, Exception, Unit] =
    for
      store <- ZIO.service[H2Store]
      id    <- store.addTodo( Todo(task = "mow yard") )
      _     <- Console.printLine(s"Todo id: $id")
      todos <- store.listTodos
      _     <- Console.printLine(s"Todos: $todos")
      ct    <- store.updateTodo( todos.head.copy(task = "mowed yard") )
      _     <- Console.printLine(s"Update count: $ct")
      dones <- store.listTodos
      _     <- Console.printLine(s"Dones: $dones")
    yield ()

  def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app.provide(H2Store.layer)