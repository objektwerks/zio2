package objektwerks

import com.typesafe.config.Config

import io.getquill.*
import io.getquill.jdbczio.Quill.H2

import java.io.IOException
import java.sql.SQLException

import zio.{Console, Runtime, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}
import io.getquill.jdbczio.Quill
import io.getquill.jdbczio.Quill.Postgres

final case class PostgreSqlStore(quill: Quill.Postgres[SnakeCase]):
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

object PostgreSqlStore:
  val layer: ZLayer[Postgres[SnakeCase], Nothing, PostgreSqlStore] = ZLayer.fromFunction(apply(_))

object QuillPostgreSqlApp extends ZIOAppDefault:
  def app: ZIO[PostgreSqlStore, Exception, Unit] =
    for
      store <- ZIO.service[PostgreSqlStore]
      id    <- store.addTodo( Todo(task = "clean pool") )
      _     <- Console.printLine(s"Todo id: $id")
      todos <- store.listTodos
      _     <- Console.printLine(s"Todos: $todos")
      ct    <- store.updateTodo( todos.head.copy(task = "cleaned pool") )
      _     <- Console.printLine(s"Update count: $ct")
      dones <- store.listTodos
      _     <- Console.printLine(s"Dones: $dones")
    yield ()

  def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] =
    app
      .provide(
        PostgreSqlStore.layer,
        Quill.Postgres.fromNamingStrategy(SnakeCase),
        Quill.DataSource.fromConfig(Resources.loadConfig("quill.conf", "pg"))
      )
      .debug("Results")
      .exitCode