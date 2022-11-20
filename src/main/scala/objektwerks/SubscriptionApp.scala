package objektwerks

import zio.{Runtime, Scope, Task, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

case class User(name: String, email: String)

case class Connection():
  def run(query: String): Task[Unit] = ZIO.succeed(println(s"Executing query: $query"))

class ConnectionPool(number: Int):
  def connect: Task[Connection] = ZIO.succeed(println("Acquired connection.")) *> ZIO.succeed(Connection())

object ConnectionPool:
  def apply(number: Int): ConnectionPool = new ConnectionPool(number)
  def layer: ZLayer[Int, Nothing, ConnectionPool] = ZLayer.fromFunction(apply)

class DatabaseService(connectionPool: ConnectionPool):
  def add(user: User): Task[Unit] =
    for
      connection <- connectionPool.connect
      _          <- connection.run(s"add $user")
    yield ()

object DatabaseService:
  def apply(connectionPool: ConnectionPool): DatabaseService = new DatabaseService(connectionPool)
  def layer: ZLayer[ConnectionPool, Nothing, DatabaseService] = ZLayer.fromFunction(apply)

class EmailService:
  def email(user: User): Task[Unit] =
    ZIO.succeed(println(s"You're subscribed! Welcome, ${user.name}!")).unit

object EmailService:
  def apply(): EmailService = new EmailService()
  def layer: ZLayer[Any, Nothing, EmailService] = ZLayer.succeed(apply())

class SubscriptionService(emailService: EmailService, database: DatabaseService):
  def subscribe(user: User): Task[Unit] =
    for
      _ <- emailService.email(user)
      _ <- database.add(user)
    yield ()

object SubscriptionService:
  def apply(emailService: EmailService, databaseService: DatabaseService): SubscriptionService = SubscriptionService(emailService, databaseService)
  def layer: ZLayer[Any, Nothing, SubscriptionService] = ZLayer.fromFunction(apply)

object SubscriptionApp extends ZIOAppDefault:
  val app: ZIO[SubscriptionService, Throwable, Unit] =
    for
      service <- ZIO.service[SubscriptionService]
      _       <- service.subscribe( User("Fred Flintstone", "fred.flintstone@rock.com") )
      _       <- service.subscribe( User("Barney Rubble", "barney.rubble@rock.com") )
    yield ()

  override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app.provide(SubscriptionService.layer)