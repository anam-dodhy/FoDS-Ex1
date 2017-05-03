/**
  * Created by adodh on 5/2/2017.
  */

import java.net.{DatagramPacket, DatagramSocket, InetAddress}

object server{
  def main(args : Array[String]): Unit = {
    val udpSocket = new DatagramSocket((9899))
    println("Server is running...")
    receiveMsg(udpSocket)
  }

  def receiveMsg(udpSocket: DatagramSocket) {
    val text = ".............".map(_.toByte).toArray
    val packet = new DatagramPacket(text, text.length, InetAddress.getByName("localhost"), 9899)

    while(true) {
      udpSocket.receive(packet)
      var msgReceived = new String(packet.getData())
      println(msgReceived)
      sendMsg(msgReceived, udpSocket)

      //if end received then terminate (RETURN)
    }
  }

  def sendMsg(msgReceived : String, udpSocket : DatagramSocket): Unit ={
    val text = (msgReceived + ":echoed").map(_.toByte).toArray
    val reply = new DatagramPacket(text, text.length, InetAddress.getByName("localhost"), 9898)
    udpSocket.send(reply)
  }

}
