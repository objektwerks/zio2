package objektwerks

import zio.{Scope, ZIO, ZIOAppArgs, ZIOAppDefault}
import zio.stm.{TRef, STM}

object STMApp extends ZIOAppDefault:
  def transferMoney(senderAccount: TRef[Int],
                    receiverAccount: TRef[Int], 
                    transferAmount: Int): STM[String, Int] =
    for
      senderBalance   <- senderAccount.get
      _               <- if (senderBalance < transferAmount) then STM.fail(s"Insufficient funds: [ $senderBalance < $transferAmount ]")
                         else STM.unit
      _               <- senderAccount.update(accountBalance => accountBalance - transferAmount)
      _               <- receiverAccount.update(accountBalance => accountBalance + transferAmount)
      receiverBalance <- receiverAccount.get
    yield receiverBalance

  def app(senderAccountBalance: Int,
          receiverAccountBalance: Int,
          transferAmount: Int): ZIO[Any, String, Int] =
    for
      _               <- ZIO.succeed(println(s"Sender account balance: $senderAccountBalance"))
      _               <- ZIO.succeed(println(s"Receiver account balance: $receiverAccountBalance"))
      _               <- ZIO.succeed(println(s"Transfer ammount: $transferAmount"))
      senderAccount   <- STM.atomically(TRef.make(senderAccountBalance))
      receiverAccount <- STM.atomically(TRef.make(receiverAccountBalance))
      receiverAmount  <- STM.atomically(transferMoney(senderAccount, receiverAccount, transferAmount))
      _               <- ZIO.succeed(println(s"Receiver account balance: $receiverAmount"))
    yield receiverAmount

  override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] =
    app(senderAccountBalance   = 1000,
        receiverAccountBalance = 0,
        transferAmount         = 500)
      .map(receiverAmount => assert(receiverAmount == 500))