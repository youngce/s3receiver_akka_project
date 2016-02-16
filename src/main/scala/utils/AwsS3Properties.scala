package utils


import com.amazonaws.ClientConfiguration
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.transfer.TransferManager
import com.amazonaws.util.IOUtils

/**
 * Created by mark on 2015/9/3.
 */
object AwsS3Properties {
  private def conf: ClientConfiguration ={
    val config=new ClientConfiguration()
    config.setProxyHost("10.57.35.31")
    config.setProxyPort(8080)
    config.setMaxErrorRetry(10)
    config
  }
  private val AWS_ACCESS_KEY =???
  private val AWS_SECRET_KEY =???
  private val awsCredentials: BasicAWSCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY)
  val BUCKET_NAME =???
  val PREFIX_PATH= ???
  val transferManager= new TransferManager(awsCredentials)
  val client = new AmazonS3Client(awsCredentials,conf)
  def getObject(key:String): Array[Byte] ={
    val is=client.getObject(BUCKET_NAME,s"$PREFIX_PATH/$key").getObjectContent
    IOUtils.toByteArray(is)
  }
}
