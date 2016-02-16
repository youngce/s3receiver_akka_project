/**
 * Created by mark on 2015/8/26.
 */
object Exts {
  implicit def StringExts(str: String) = new {
    import org.json4s._
    import org.json4s.native.JsonMethods._
    def isJson :Boolean={
      import scala.util.{Try, Success, Failure}
      Try(parse(str)) match {
        case Success(v)=>true
        case Failure(ex)=>false
      }
    }
    def toByteArray={
      str.map(_.toByte).toArray
    }
  }
  implicit def IntExts(i:Int)=new {
    def toByteArray = {
      java.nio.ByteBuffer.allocate(4).putInt(i).array()
    }
  }



}
