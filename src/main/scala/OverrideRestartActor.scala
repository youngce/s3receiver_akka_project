import akka.actor.{OneForOneStrategy, SupervisorStrategy, Actor}

/**
 * Created by mark on 2015/9/3.
 */
trait OverrideRestartActor extends Actor{
  def restartImpl(msg:Any):Unit
  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    message foreach { restartImpl(_) }
  }
}
trait RetryActor extends OverrideRestartActor{
  override def restartImpl(msg:Any):Unit={self ! msg}
}
