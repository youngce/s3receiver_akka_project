//
//import MetadataManager.GetNotReadMetadata
//import ReceiveManager.Start
//import akka.actor.{Props, ActorSystem}
//import com.mongodb.casbah.Imports._
//import com.mongodb.casbah.commons.TypeImports
//import com.typesafe.config.ConfigFactory
//import org.scalatest.{Matchers, FlatSpec}
//
///**
// * Created by mark on 2015/9/1.
// */
//class MetaDataManagerSpec extends FlatSpec with Matchers{
//  val config = ConfigFactory.parseString("""
//  akka.loglevel = "DEBUG"
//  akka.actor.debug {
//    receive = on
//    lifecycle = on
//  }""")
//  val system = ActorSystem("testSystem",config)
//  def cpMetaData: Unit ={
//    val USER="dcuser"
//    val PASSWORD="zXYonYKH"
//    val IP_ADDRESS="10.57.41.137"
//    val PORT=28017
//    val DB_NAME="dcdb"
//    val COLLECTION_NAME= "UploadDataRecords"
//    def uri=MongoClientURI(s"mongodb://$USER:$PASSWORD@$IP_ADDRESS:$PORT/$DB_NAME")
//    val remote=MongoClient(uri)(DB_NAME)(COLLECTION_NAME)
//    import utils.MongoDBProperties._
//
//
//    val meta =remote.take(10).map(v=>v.toList)
//    client
//    //val bulk=collection.initializeOrderedBulkOperation
//    //meta.foreach(v=>bulk.insert(MongoDBObject(v)))
//    //collection.insert(meta.map(v=>MongoDBObject(v)))
//    //bulk.execute()
//  }
//  "mongodb connection test" should "success" in {
//
//    val USER="dcowner"
//    val PASSWORD="9btoNeA0"
//    val IP_ADDRESS="52.76.56.170"
//    val PORT=28017
//    val DB_NAME="dcdb"
//    val COLLECTION_NAME= "UploadDataRecords"
//    def uri=MongoClientURI(s"mongodb://$USER:$PASSWORD@$IP_ADDRESS:$PORT/$DB_NAME")
//    val client=MongoClient(uri)
//    //client.setWriteConcern()
//    val remote=client(DB_NAME)(COLLECTION_NAME)
//    val res=remote.take(10)
//    res.size shouldBe(10)
//
//  }
//
////  "test" should "test" in {
////    //val ids=List("55d6d73c8573958d2d8b4568","55d6d73c8573958a2d8b4569")
////    val manager=system.actorOf(Props[ReceiveManager],"test")
////    manager!ReceiveManager.Start
////    system.awaitTermination()
////    //Thread.sleep(10*60*1000)
////    1 should be(1)
////  }
//
//
//}
