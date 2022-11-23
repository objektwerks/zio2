package objektwerks

import zio.{ZIO, ZIOAppDefault}

import zio.{durationInt, Console, Scope, Semaphore, ZIO, ZIOAppArgs, ZIOAppDefault}
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

  val app: ZIO[Any, String, Int] = for
    senderAccount   <- STM.atomically(TRef.make(1000))
    receiverAccount <- STM.atomically(TRef.make(0))
    receiverAmount  <- STM.atomically(transferMoney(senderAccount, receiverAccount, 500))
  yield receiverAmount

  override def run: ZIO[Environment & (ZIOAppArgs & Scope), Any, Any] = app