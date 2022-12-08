package objektwerks

import com.typesafe.config.{Config, ConfigFactory}

import io.getquill.*
import io.getquill.jdbczio.Quill
import io.getquill.jdbczio.Quill.H2

import java.io.IOException
import java.nio.file.Path
import javax.sql.DataSource
import java.sql.SQLException

import zio.{Runtime, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}
import zio.logging.{LogFormat, file}

case class Todo(id: Int = 0, task: String)

case class Store(quill: Quill.H2[SnakeCase]):
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
  def dataSourceLayer(config: Config): ZLayer[Any, Throwable, DataSource] = Quill.DataSource.fromConfig(config)

  val namingStrategyLayer: ZLayer[DataSource, Nothing, H2[SnakeCase]] = Quill.H2.fromNamingStrategy(SnakeCase)

  val layer: ZLayer[H2[SnakeCase], IOException, Store] = ZLayer.fromFunction(apply(_))

object QuillApp extends ZIOAppDefault:
  override val bootstrap: ZLayer[ZIOAppArgs, Any, Environment] =
    Runtime.removeDefaultLoggers >>> file(Path.of("./target/quill.log"))

  def app: ZIO[Store, Exception, Unit] =
    for
      store <- ZIO.service[Store]
      id    <- store.addTodo( Todo(task = "mow yard") )
      _     <- ZIO.log(s"Todo id: $id")
      todos <- store.listTodos
      _     <- ZIO.log(s"Todos: $todos")
      ct    <- store.updateTodo( todos(0).copy(task = "mowed yard") )
      _     <- ZIO.log(s"Update count: $ct")
      dones <- store.listTodos
      _     <- ZIO.log(s"Dones: $dones")
    yield ()

  def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] =
    for
      config <- Resources.loadConfig(path = "quill.conf", section = "db")
    yield app
            .provide(
              Store.layer,
              Store.dataSourceLayer(config),
              Store.namingStrategyLayer
            )