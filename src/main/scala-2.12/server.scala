/**
  * Created by adodh on 5/2/2017.
  */

import java.net.{DatagramPacket, DatagramSocket, InetAddress}

object server {
  def main(args: Array[String]): Unit = {

    println("Server is running...")
    sendOrRecServer(Msg(""), "Receiver")
  }

  val udpSocket = new DatagramSocket(9899)

  def sendOrRecServer(msg: MsgType, senderOrReceiver: String): Unit = msg match {
    case Msg(message: String) => {
      if (senderOrReceiver == "Receiver") {
        val text = ".............".map(_.toByte).toArray
        val packet = new DatagramPacket(text, text.length, InetAddress.getByName("localhost"), 9899)

        while (true) {
          udpSocket.receive(packet)
          var msgReceived = new String(packet.getData())
          println(msgReceived)
          sendOrRecServer(Msg(msgReceived), "Sender")
        }
      }
      if (senderOrReceiver == "Sender") {
        val text = (message + ":echoed").map(_.toByte).toArray
        val reply = new DatagramPacket(text, text.length, InetAddress.getByName("localhost"), 9898)
        udpSocket.send(reply)
      }
    }
  }
}
