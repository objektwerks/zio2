package objektwerks

import com.typesafe.config.{Config, ConfigFactory}

import java.sql.SQLException

import zio.{Console, Runtime, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

import io.getquill.*
import io.getquill.jdbczio.Quill.H2

case class Todo(id: Int = 0, task: String)

trait Store:
  def addTodo(todo: Todo): ZIO[Any, SQLException, Int]
  def updateTodo(todo: Todo): ZIO[Any, SQLException, Long]
  def listTodos: ZIO[Any, SQLException, List[Todo]]

object Store:
  def addTodo(todo: Todo): ZIO[Store, SQLException, Int] = ZIO.serviceWithZIO[Store](_.addTodo(todo))
  def updateTodo(todo: Todo): ZIO[Store, SQLException, Long] = ZIO.serviceWithZIO[Store](_.updateTodo(todo))
  def listTodos: ZIO[Store, SQLException, List[Todo]] = ZIO.serviceWithZIO[Store](_.listTodos)

// ZIO Quill cannot life case classes like old Quill. Why?
case class DefaultStore(config: Config) extends Store:
  val ctx = H2(SnakeCase, new H2JdbcContext(SnakeCase, config).dataSource)
  import ctx.*

  inline def addTodo(todo: Todo): ZIO[Any, SQLException, Int] =
    run( 
      query[Todo]
        .insert( lift(todo => (todo.id, todo.task)) )
        .returningGenerated(_.id)
    )

  inline def updateTodo(todo: Todo): ZIO[Any, SQLException, Long] =
    run(
      query[Todo]
        .filter(_.id == lift(todo.id))
        .update( lift(todo => (todo.id, todo.task)) )
    )

  inline def listTodos: ZIO[Any, SQLException, List[Todo]] = run( query[Todo] )

object DefaultStore:
  val layer: ZLayer[Any, Nothing, Store] =
    ZLayer {
      ZIO.succeed(DefaultStore( ConfigFactory.load("quill.conf") ))
    }

object QuillApp extends ZIOAppDefault:
  def app: ZIO[Store, Exception, Unit] =
    for
      id    <- Store.addTodo( Todo(task = "mow yard"))
      _     <- Console.printLine(s"Todo id: $id")
      todos <- Store.listTodos
      _     <- Console.printLine(s"Todos: $todos")
      _     <- Store.updateTodo( todos(0).copy(task = "mowed yard"))
      _     <- Console.printLine(s"Todos: ${Store.listTodos}")
    yield ()

  def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app.provide(DefaultStore.layer)