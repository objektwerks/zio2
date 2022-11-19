import zio.{Task, ZIO}

case class User(name: String, email: String)

case class Connection():
  def run(query: String): Task[Unit] = ???

class ConnectionPool(number: Int):
  def connect: Task[Connection] =
    ZIO.succeed(println("Acquired connection.")) *> ZIO.succeed(Connection())

class UserDatabase(connectionPool: ConnectionPool):
  def add(user: User): Task[Unit] =
    for
      connection <- connectionPool.connect
      _          <- connection.run(s"add $user")
    yield()

class EmailService:
  def email(user: User): Task[Unit] = ZIO.succeed(s"You're subscribed! Welcome, ${user.name}!").unit

class UserSubscription(emailService: EmailService, userDatabase: UserDatabase):
  def subscribe(user: User): Task[Unit] =
    for
      _ <- emailService.email(user)
      _ <- userDatabase.add(user)
    yield()
