package Esercitazione_2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TimeServerClientDatagram {

    public static void main(String...args){
        try{
            String hostName="localhost";
            DatagramSocket socket = new DatagramSocket();
            //invia la richiesta
            byte[] buf = new byte[256];
            InetAddress address = InetAddress.getByName(hostName);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 3575);
            socket.send(packet);
            //riceve la risposta
            packet= new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            //visualizza risposta
            String received = new String(packet.getData());
            System.out.println("Response: "+received);
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
