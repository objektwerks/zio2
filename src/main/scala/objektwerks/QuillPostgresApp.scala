package objektwerks

import io.getquill.*
import io.getquill.jdbczio.Quill
import io.getquill.jdbczio.Quill.Postgres

import java.sql.SQLException

import zio.{Console, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

final case class PostgresStore(quill: Quill.Postgres[Literal]):
  import quill.*

  def addTodo(todo: Todo): ZIO[Any, SQLException, Int] =
    run( query[Todo].insertValue( lift(todo) ).returningGenerated(_.id) )

  def updateTodo(todo: Todo): ZIO[Any, SQLException, Long] =
    run( query[Todo].filter(_.id == lift(todo.id)).updateValue( lift(todo) ) )

  def listTodos: ZIO[Any, SQLException, List[Todo]] =
    run( query[Todo] )

object PostgresStore:
  val layer: ZLayer[Postgres[Literal], Nothing, PostgresStore] = ZLayer.fromFunction(apply(_))

object QuillPostgreSqlApp extends ZIOAppDefault:
  def app: ZIO[PostgresStore, Exception, Unit] =
    for
      store <- ZIO.service[PostgresStore]
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
        Quill.DataSource.fromConfig(Resources.loadConfig("quill.conf", "pg")),
        Quill.Postgres.fromNamingStrategy(Literal),
        PostgresStore.layer
      )
      .debug
      .exitCode