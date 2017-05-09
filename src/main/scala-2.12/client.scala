/**
  * Created by adodh on 5/2/2017.
  */
import java.net.{DatagramPacket, DatagramSocket, InetAddress}
import com.twitter.chill.KryoInjection

abstract sealed class MsgType
case class Msg(message : String ) extends MsgType
case class End () extends MsgType

object client{

  def main(args : Array[String]): Unit = {

    println("Client is running...")
    sendOrRecClient(Msg("Hello Server"),"Sender")
    sendOrRecClient(Msg(""),"Receiver")
    sendOrRecClient(End(),"End")
  }

  val udpSocket = new DatagramSocket(9897)

  def sendOrRecClient(msg:MsgType,senderOrReceiver:String):Unit = msg match{
    case Msg(message:String) => {
           if(senderOrReceiver  == "Sender"){


              val text:  Array[Byte] = KryoInjection(message)
              //val text = message.map(_.toByte).toArray
              val packet = new DatagramPacket(text, text.length, InetAddress.getByName("localhost"), 9896)
              udpSocket.send(packet)
            }
            if(senderOrReceiver == "Receiver"){
              val text = "........................".map(_.toByte).toArray
              val packet = new DatagramPacket(text, text.length, InetAddress.getByName("localhost"), 9897)
              udpSocket.receive(packet)
              var msgReceived = new String(packet.getData())
              println(msgReceived)
              sendOrRecClient(Msg(msgReceived),"Sender")
            }

    }

    case End() => {
      udpSocket.close()
    }
  }

}
