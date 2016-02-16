import ReceiveManager.{Downloading,  Start}
import MetadataManager.{Completed, GetNotReadMetadata}
import akka.actor.SupervisorStrategy.{Resume, Stop, Escalate, Restart}
import akka.actor._
import akka.actor.Actor.Receive
import akka.event.LoggingReceive
import akka.util.Timeout
import com.mongodb.MongoException
import org.apache.http.NoHttpResponseException
import org.joda.time.{Period, DateTime}

import scala.collection.mutable.ListBuffer
import scala.concurrent.duration.FiniteDuration

/**
 * Created by mark on 2015/9/3.
 */

object ReceiveManager{
  case object Start
  case class Downloading(timestamp:DateTime,status: DownloadingStatus)
  case class CheckTimeOut(interval:FiniteDuration)

  sealed trait DownloadingStatus
  case object Started extends DownloadingStatus
  case object Completed extends DownloadingStatus
  case object Saved extends DownloadingStatus
  
  sealed trait StatusMsg{val id:String;def updateStatus(dl:Downloading):Downloading}
  //case class Started(id:String) extends StatusMsg
  case class Completed(id:String) extends StatusMsg{
    def updateStatus(dl:Downloading): Downloading = Downloading(dl.timestamp,Completed)
  }
  case class Saved(id:String) extends StatusMsg{
    def updateStatus(dl:Downloading): Downloading = Downloading(dl.timestamp,Saved)
  }
  

}
class ReceiveManager extends Actor{
  import ReceiveManager._
  import akka.actor.Props
  import scala.concurrent.duration.Duration



  val metadataManager=context.actorOf(Props[MetadataManager],"MetadataManager")
  var downloadings:Map[String,Downloading]=Map.empty
  import scala.concurrent.duration._
  override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy(-1,Duration.Inf){
    case _:MongoException=>Restart
    case _:InvalidActorNameException=>Resume
    case _:Throwable=>Stop
  }


  override def receive: Receive = {
    case Start=> {
      import context.dispatcher
      context.system.scheduler.schedule(Duration.Zero,1 seconds,metadataManager,GetNotReadMetadata)
    }
  }
  override def preStart: Unit ={
   self ! Start
  }
}
