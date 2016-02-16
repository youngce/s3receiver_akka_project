//import java.util.Properties
//
//import kafka.api.FetchRequestBuilder
//import kafka.consumer.ConsumerConfig
//import org.apache.kafka.clients.consumer.Consumer
//import utils.KafkaProperties
//
//import scala.collection.immutable.HashMap
//
///**
// * Created by mark on 2015/9/4.
// */
//object ConsumerTesting {
////  val props = new Properties();
////  props.put("zookeeper.connect", KafkaProperties.ZK_HOST+":"+KafkaProperties.ZK_PORT);
////  props.put("group.id", KafkaProperties.GROUP_ID);
////  props.put("zookeeper.session.timeout.ms", "400");
////  props.put("zookeeper.sync.time.ms", "200");
////  props.put("auto.commit.interval.ms", "1000");
////  val consumerConfig=new ConsumerConfig(props)
////  val consumer=kafka.consumer.Consumer.createJavaConsumerConnector(consumerConfig);
////  val topicCount =new java.util.HashMap[String,Integer]()
////  topicCount.put(KafkaProperties.TOPIC,1)
////  val consumerMap=consumer.createMessageStreams(topicCount);
////  val stream=consumerMap.get(KafkaProperties.TOPIC).get(0);
////  for (v<-stream){
////    println(s"key: ${new String(v.key())}")
////  }
////  stream.foreach(v=>{
////    println(s"key: ${new String(v.key())}")
////  })
//
//
////  val req = new FetchRequestBuilder()
////    .clientId(KafkaProperties.GROUP_ID)
////    .addFetch(KafkaProperties.TOPIC, 0, 0L, 3*Math.pow(10,10).toInt)
////    .build();
////
////  val resp=KafkaProperties.simpleConsumer.fetch(req)
////  val msgs=resp.messageSet(KafkaProperties.TOPIC,0).shallowIterator.map(_.message)
////  //val size=msgs.size
////  val keys=msgs.filter(_.hasKey).map(v=>{
////    val limit=v.key.limit()
////    val bytes=Array.fill(limit)(0.toByte)
////    v.key.get(bytes)
////    new String(bytes)
////    //new String(v.key.array())
////  }).toList
////
////  println(keys.size)
////  if (keys.distinct.size!=keys.size)
////    throw new Exception(s"keys.distinct=${keys.distinct.size}, key=${keys.size}")
//
//
//}
