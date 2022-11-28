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

case class DefaultStore(config: Config) extends Store:
  val ctx = H2(SnakeCase, new H2JdbcContext(SnakeCase, config).dataSource)
  import ctx.*

  inline def addTodo(todo: Todo): ZIO[Any, SQLException, Int] =
    run( 
      query[Todo]
        .insert( lazyLift(todo) )
        .returningGenerated(_.id)
    )

  inline def updateTodo(todo: Todo): ZIO[Any, SQLException, Long] =
    run(
      query[Todo]
        .filter(_.id == lazyLift(todo.id))
        .update( lazyLift(todo) )
    )

  inline def listTodos: ZIO[Any, SQLException, List[Todo]] = run( query[Todo] )

object DefaultStore:
  val layer: ZLayer[Any, Nothing, Store] =
    ZLayer {
      ZIO.succeed(DefaultStore( ConfigFactory.load("quill.conf") ))
    }

object QuillApp extends ZIOAppDefault:
  def app: ZIO[Any, Exception, Unit] =
    for
      _  <- Console.printLine("TODO!")
    yield ()

  def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app.provide(DefaultStore.layer)