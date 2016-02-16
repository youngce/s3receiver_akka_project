//import com.mongodb.DBObject
//import utils.MongoDBProperties
//
////package tmp
//
///**
// * Created by mark on 2015/9/4.
// */
//object PseudoMetadata{
//  import sys.process._
//  val src:String="/home/mark/Git/s3logsscheduler/s3Logs/201503"//1668ca98016d4b2dbe871d4fd62b60e3
//  val list =(s"find $src -name APPCmdHeader"!!).split("\n")
//  import org.json4s._
//  import org.json4s.native.Serialization
//  import org.json4s.native.Serialization.write
//   implicit val formats = Serialization.formats(NoTypeHints) ++ ext.JodaTimeSerializers.all
//  import com.mongodb.util.JSON
//  val docs=list.map(v=>v.split("/").dropRight(1).mkString("/")->scala.io.Source.fromFile(v).mkString).map(v=>v._1->AppCmdHeader(v._2).asInstanceOf[AppCmdHeader]).map(v=>v._1->write(v._2)).map(v=>v._1->JSON.parse(v._2).asInstanceOf[DBObject]).map(obj=>{
//    obj._2.put("IsRead",false)
//    obj._2.put("UploadDataPath",obj._1+"/"+"Contents")
//    obj._2
//
//  })
//  MongoDBProperties.collection.drop()
//  import com.mongodb.casbah.Imports._
//  val builder=MongoDBProperties.collection.initializeOrderedBulkOperation
//  docs.foreach(doc=>{
//    builder.insert(doc)
//  })
//  builder.find("_id" $ne "A00000498F8E10").update($set("IsRead"->false))
// builder.execute()
//}
