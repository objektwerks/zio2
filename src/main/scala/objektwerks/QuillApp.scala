package objektwerks

import com.typesafe.config.{Config, ConfigFactory}

import java.sql.SQLException

import zio.{Console, Runtime, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

import io.getquill.*
import io.getquill.jdbczio.Quill.H2

case class Todo(id: Int = 0, task: String)

case class Store():
  val conf = ConfigFactory.load("quill.conf")
  val ctx = H2(SnakeCase, new H2JdbcContext(SnakeCase, conf).dataSource)
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

object QuillApp extends ZIOAppDefault:
  def app: ZIO[Any, Exception, Unit] =
    for
      _  <- Console.printLine("TODO!")
    yield ()

  def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app