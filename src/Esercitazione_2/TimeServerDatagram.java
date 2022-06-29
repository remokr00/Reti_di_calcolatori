package Esercitazione_2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class TimeServerDatagram {

    public static void main(String...args){
        try{
            DatagramSocket socket = new DatagramSocket(3575);
            int n=1;
            //aspetto massimo 10 datagrammi
            while(n<=10) {
                byte[] buf = new byte[256];
                //riceve la richiesta
                DatagramPacket packet = new DatagramPacket(buf, buf.length); //quando ricevo il datagramma uso sempre due parametri buffer e la sua lunghezza
                socket.receive(packet);
                //produce risposta
                String dString = new Date().toString();
                buf = dString.getBytes();
                //invia la risposta al client
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port); //quando devo inviare un packet devo specificare altri due parametri Indirizzo IP e porta del client
                socket.send(packet);
                n++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
