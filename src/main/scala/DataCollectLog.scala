
import java.util.concurrent.TimeUnit
import akka.actor.{PoisonPill}
import akka.event.LoggingReceive
import com.mongodb.casbah.Imports._
import kafka.controller.Callbacks
import org.apache.kafka.clients.producer.{ProducerRecord, RecordMetadata}

import org.bson.types.ObjectId
import org.joda.time.DateTime
import org.json4s.JsonAST.JValue
import utils.{KafkaProperties, MongoDBProperties, AwsS3Properties}

/**
 * Created by mark on 2015/9/4.
 */
object DataCollectLog{
  case class GetContent(path:String)
  case class Save(content:Array[Byte])
  case class ModifyMetadata(id:String)
  case object Completed
}
class DataCollectLog(id:String,header:JValue) extends RetryActor{
  import DataCollectLog._
  import Exts.IntExts
  import Exts.StringExts
  //require(header,s"the format isn't json, header:$header")
  implicit def format=org.json4s.DefaultFormats
  val topic=(header\"DCVersion").extract[String].toInt match {
    case i:Int if i<20 => "v1.x"
    case i:Int if 20 <= i && i < 300 => "v2.x"
  }

  case class RawLog(header:JValue,content:Array[Byte]){
   // private implicit val format=org.json4s.DefaultFormats

    import org.json4s.native.Serialization.write
    def toJson()={
      write(this)
    }
  }

  def merge(header:String,content:Array[Byte]): Array[Byte] ={
    val len=header.length.toByteArray
    (len++header.toByteArray)++content
  }

  override def receive: Receive = LoggingReceive{
    case GetContent(path)=> self !Save(AwsS3Properties.getObject(path))
    case Save(content)=> {
      val rawLog=RawLog(header,content).toJson()


      KafkaProperties.producer
        .send(new ProducerRecord[String,String](topic,null,rawLog.replace("\\","")),null)
        .get()
      //KafkaProperties.save(null,rawLog.replace("\\",""),null).get(1,TimeUnit.SECONDS)

      self ! ModifyMetadata(id)
    }
    case ModifyMetadata(id)=> {
      MongoDBProperties.collection.findAndModify("_id" $eq new ObjectId(id),$set("IsRead"-> !MongoDBProperties.NotReadValue,"Read_Timestamp"->DateTime.now.toString(utils.MongoDBProperties.DATETIME_FORMAT)))
      self!Completed
    }
    case Completed=>self ! PoisonPill
  }
}
