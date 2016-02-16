import akka.actor._
import com.typesafe.config.ConfigFactory

/**
 * Created by mark on 2015/9/7.
 */
object Main extends App{
  val config = ConfigFactory.parseString("""
  akka.loglevel = "WARNING"
  akka.actor.debug {
    receive = on
    lifecycle = on
  }
 akka{
     jvm-exit-on-fatal-error = off
 }""")
  val system = ActorSystem("SysActor")
  system.actorOf(Props[ReceiveManager],"receiveManager")
  //system.awaitTermination()


}


