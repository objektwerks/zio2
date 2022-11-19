import zio.{Task, ZIO}

case class User(name: String, email: String)

case class Connection():
  def run(query: String): Task[Unit] = ???

class ConnectionPool(number: Int):
  def connect: Task[Connection] = ???

class UserDatabase(connectionPool: ConnectionPool):
  def add(user: User): Task[Unit] = ???

class EmailService:
  def email(user: User): Task[Unit] = ZIO.succeed(s"You're subscribed! Welcome, ${user.name}")

class UserSubscription(emailService: EmailService, userDatabase: UserDatabase):
  def subscribe(user: User): Task[Unit] =
    for
      _ <- emailService.email(user)
      _ <- userDatabase.add(user)
    yield()
