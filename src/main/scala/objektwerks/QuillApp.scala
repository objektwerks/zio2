package objektwerks

import com.typesafe.config.Config

import io.getquill.*
import io.getquill.jdbczio.Quill.H2

import java.io.IOException
import java.sql.SQLException

import zio.{Console, Runtime, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

final case class Todo(id: Int = 0, task: String)

final case class Store(config: Config):
  val quill = H2(SnakeCase, new H2JdbcContext(SnakeCase, config).dataSource)
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

object Store:
  def layer(config: Config): ZLayer[Any, IOException, Store] = ZLayer.succeed( Store(config) )

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

  def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] =
    for
      config <- Resources.loadConfig(path = "quill.conf", section = "db")
    yield app.provide( Store.layer(config) )