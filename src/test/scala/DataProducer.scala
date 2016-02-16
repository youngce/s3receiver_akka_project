//import akka.actor.{ActorSystem, Props}
//import com.typesafe.config.ConfigFactory
//
///**
// * Created by mark on 2015/9/7.
// */
//object DataProducer {
//  val config = ConfigFactory.parseString("""
//  akka.loglevel = "DEBUG"
//  akka.actor.debug {
//    receive = on
//    lifecycle = on
//  }""")
//  val system = ActorSystem("testSystem",config)
//  val manager=system.actorOf(Props[ReceiveManager],"test")
//  manager!ReceiveManager.Start
//  system.awaitTermination()
//}
