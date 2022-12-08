package objektwerks

import com.typesafe.config.{Config, ConfigFactory}

import io.getquill.*
import io.getquill.jdbczio.Quill.H2

import java.io.IOException
import java.sql.SQLException

import zio.{Console, Runtime, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

case class Todo(id: Int = 0, task: String)

case class Store(config: Config):
  val ctx = H2(SnakeCase, new H2JdbcContext(SnakeCase, config).dataSource)
  import ctx.*

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

object Store:
  val layer: ZLayer[Any, IOException, Store] =
    ZLayer {
      for
        config <- Resources.loadConfig(path = "quill.conf", section = "quill.ctx")
      yield Store(config)
    }

object QuillApp extends ZIOAppDefault:
  def app: ZIO[Store, Exception, Unit] =
    for
      store <- ZIO.service[Store]
      id    <- store.addTodo( Todo(task = "mow yard") )
      _     <- Console.printLine(s"Todo id: $id")
      todos <- store.listTodos
      _     <- Console.printLine(s"Todos: $todos")
      ct    <- store.updateTodo( todos(0).copy(task = "mowed yard") )
      _     <- Console.printLine(s"Update count: $ct")
      dones <- store.listTodos
      _     <- Console.printLine(s"Dones: $dones")
    yield ()

  def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app.provide(Store.layer)