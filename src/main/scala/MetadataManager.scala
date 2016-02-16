
import MetadataManager.{GetNotReadMetadata, Completed}
import akka.actor.SupervisorStrategy.{Stop, Resume, Restart}
import akka.actor._
import akka.event.LoggingReceive
import akka.util.Timeout
import com.mongodb.MongoException

import org.bson.types.ObjectId
import org.json4s._
import utils.MongoDBProperties
import utils.MongoDBProperties._

import scala.concurrent.duration._
import com.mongodb.casbah.Imports._
import org.joda.time.DateTime

import scala.util.Try

/**
 * Created by mark on 2015/9/1.
 */
import Exts.StringExts
case class Metadata(id:String,uploadDataPath:String,isRead:Boolean,header:JValue){
  require(isRead==MongoDBProperties.NotReadValue,"this metadata was read.")
  //require(header.isJson,s"this is not json format header: $header")
}
object MetadataManager{
  case class Completed(id:String)
  case object GetNotReadMetadata

}
case class ModifyException(modifyMsg:ModifyMsg,ex:MongoException)
case class ModifyMsg(id:String,isRead:Boolean,readTs:DateTime)
class MetadataModifier extends Actor{
  override def receive: Actor.Receive = {
    case msg@ModifyMsg(id,isRead,readTs)=>
      val timestamp=readTs.toString(DATETIME_FORMAT)
      Try(collection.findAndModify("_id" $eq new ObjectId(id),$set("IsRead"->isRead,"Read_Timestamp"->timestamp)))
        .recover{case ex:MongoException=>sender() ! ModifyException(msg,ex)} .foreach(v=>sender()!Completed(id))

  }
}

class MetadataManager extends Actor{

  implicit val timeout = Timeout(5 seconds)
  override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy(-1,Duration.Inf){
    case _:MongoException=>Restart
    case _:Throwable=>Stop
  }
  override def receive: Receive = LoggingReceive{
    case GetNotReadMetadata=>{
      val notReadMetadata=collection.find("IsRead" $eq MongoDBProperties.NotReadValue).take(10000)
      notReadMetadata.map(obj=>{
        //obj.update()
        val isRead=obj.get("IsRead").asInstanceOf[Boolean]
        val Array(id,uploadDataPath)=
          Array(obj.getAsOrElse[ObjectId]("_id", throw new NoSuchElementException("_id")).toString,
                obj.getAsOrElse[String]("UploadDataPath",throw new NoSuchElementException("UploadDataPath")))
        obj.update("_id",id)
        import org.json4s._
        import org.json4s.native.JsonMethods._
        val header: JValue =parse(obj.toString)

        Metadata(id,uploadDataPath,isRead,header)
      }).foreach(metadata=>{
        import DataCollectLog.GetContent
        val dcLogActor=context.actorOf(Props(classOf[DataCollectLog],metadata.id,metadata.header),metadata.id)
        dcLogActor ! GetContent(metadata.uploadDataPath)

      })

    }
    case Completed(id:String)=>
    //case Saved(id)=> self !ModifyMsg(id,true,DateTime.now)

    
  }

}
