import zio.{Task, ZIO}

case class User(name: String, email: String)

class ConnectionPool
class UserDatabase(connectionPool: ConnectionPool)

class EmailService

class UserSubscription(emailService: EmailService, userDatabase: UserDatabase):
  def notifyUser(user: User): Task[Unit]
