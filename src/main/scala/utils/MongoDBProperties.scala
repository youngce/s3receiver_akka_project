package utils

import com.mongodb.casbah.Imports._

/**
 * Created by mark on 2015/9/1.
 */
object MongoDBProperties {
  val USER=???
  val PASSWORD=???
  val IP_ADDRESS=???
  val PORT=???
  val DB_NAME=???
  val COLLECTION_NAME= ???
  def uri=MongoClientURI(s"mongodb://$USER:$PASSWORD@$IP_ADDRESS:$PORT/$DB_NAME")
  def client=MongoClient(uri)
  val collection=client(DB_NAME)(COLLECTION_NAME)
  val DATETIME_FORMAT="yyyy-MM-dd HH:mm:ss.SSS"
  val NotReadValue=false

}
