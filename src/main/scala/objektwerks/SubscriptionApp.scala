package objektwerks

import zio.{Runtime, Task, ZIO, ZIOAppDefault, ZLayer}

case class User(name: String, email: String)

case class Connection():
  def run(query: String): Task[Unit] =
    ZIO.succeed(println(s"Executing query: $query"))

class ConnectionPool(number: Int):
  def connect: Task[Connection] =
    ZIO.succeed(println("Acquired connection.")) *> ZIO.succeed(Connection())

class Database(connectionPool: ConnectionPool):
  def add(user: User): Task[Unit] =
    for
      connection <- connectionPool.connect
      _          <- connection.run(s"add $user")
    yield()

class EmailService:
  def email(user: User): Task[Unit] =
    ZIO.succeed(println(s"You're subscribed! Welcome, ${user.name}!")).unit

class SubscriptionService(emailService: EmailService, database: Database):
  def subscribe(user: User): Task[Unit] =
    for
      _ <- emailService.email(user)
      _ <- database.add(user)
    yield()

object SubscriptionApp extends ZIOAppDefault:
  val subscriptionServiceLayer = ZLayer.succeed(
    SubscriptionService(
      EmailService(),
      Database(ConnectionPool( 4 ))
    )
  )

  def subscribe(user: User): ZIO[SubscriptionService, Throwable, Unit] =
    for
      service <- ZIO.service[SubscriptionService]
      _       <- service.subscribe(user)
    yield ()

  val app =
    for
      _ <- subscribe( User("Fred Flintstone", "fred.flintstone@rock.com") )
      _ <- subscribe( User("Barney Rubble", "barney.rubble@rock.com") )
    yield ()

  override def run = app.provide( subscriptionServiceLayer )