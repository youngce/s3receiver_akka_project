package utils

import java.util.Properties
import java.util.concurrent.Future

import kafka.consumer.SimpleConsumer
import org.apache.kafka.clients.producer.{Callback, RecordMetadata, ProducerRecord, KafkaProducer}

/**
 * Created by mark on 2015/8/31.
 */
object KafkaProperties{
  val ZK_HOST="10.58.70.151"
  val ZK_PORT=2181
  val ZK_TIMEOUT=100
  val BUFFER_SIZE=10000
  val GROUP_ID="data-collect"
  val BROKER_LIST=List("10.58.70.151:9092","10.58.70.152:9092")
  val BYTE_ARRAY_SER_CLASSPATH="org.apache.kafka.common.serialization.ByteArraySerializer"
  val STRING_SER_CLASSPATH="org.apache.kafka.common.serialization.StringSerializer"
  val TOPIC="v1.x"
  private val producerConfig={
    val producerConfig = new Properties();
    producerConfig.put("bootstrap.servers", BROKER_LIST.mkString(","));
    producerConfig.put("client.id", GROUP_ID);
    producerConfig.put("key.serializer", STRING_SER_CLASSPATH);
    producerConfig.put("value.serializer", STRING_SER_CLASSPATH);
    producerConfig
  }
  val producer= new KafkaProducer[String, String](producerConfig)
  //val simpleConsumer = new SimpleConsumer(KAFKA_HOST, KAFKA_PORT, ZK_TIMEOUT, BUFFER_SIZE, GROUP_ID);
  def save(key:String,value:String,callback: Callback): Future[RecordMetadata] ={
    val res=producer.send(new ProducerRecord[String,String](TOPIC,key,value),callback)
    res
  }



}