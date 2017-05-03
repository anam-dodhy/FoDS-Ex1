/**
  * Created by adodh on 5/2/2017.
  */


import java.net.{DatagramPacket, DatagramSocket, InetAddress}

import test.{End, Msg}

object client{

  def main(args : Array[String]): Unit = {
    val udpSocket = new DatagramSocket(9898)
    println("Client is running...")



    sendMsg("Hello Server", udpSocket)
    receiveMsg(udpSocket)
  }


  def sendMsg(message : String, udpSocket : DatagramSocket) {
    val text = message.map(_.toByte).toArray
    val packet = new DatagramPacket(text, text.length, InetAddress.getByName("localhost"), 9899)
    udpSocket.send(packet)
  }

  def receiveMsg(udpSocket : DatagramSocket) {
    val text = "........................".map(_.toByte).toArray
    val packet = new DatagramPacket(text, text.length, InetAddress.getByName("localhost"), 9898)
    udpSocket.receive(packet)
    var msgReceived = new String(packet.getData())
    println(msgReceived)
    sendMsg(msgReceived, udpSocket)

  }
}


abstract sealed class MsgType
case class Msg(message : String ) extends MsgType
case class End ( ) extends MsgType

def send(m: MsgType): String = {
  m match {
    case msg => "abc"
    case Msg(msg) => c.sendMsg(msg)
    case End() => "end"
  }}

