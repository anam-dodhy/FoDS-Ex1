/**
  * Created by adodh on 5/2/2017.
  */
import java.net.{DatagramPacket, DatagramSocket, InetAddress}

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

  val udpSocket = new DatagramSocket(9898)

  def sendOrRecClient(msg:MsgType,senderOrReceiver:String):Unit = msg match{
    case Msg(message:String) => {
           if(senderOrReceiver  == "Sender"){
              val text = message.map(_.toByte).toArray
              val packet = new DatagramPacket(text, text.length, InetAddress.getByName("localhost"), 9899)
              udpSocket.send(packet)
            }
            if(senderOrReceiver == "Receiver"){
              val text = "........................".map(_.toByte).toArray
              val packet = new DatagramPacket(text, text.length, InetAddress.getByName("localhost"), 9898)
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
