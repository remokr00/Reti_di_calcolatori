package Multicast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastTimeClient {

    public static void main(String...arg){

        try {

            MulticastSocket client = new MulticastSocket(3576);
            InetAddress indirizzoGruppo = InetAddress.getByName("230.0.0.1");
            //mi unisco al gruppo
            client.joinGroup(indirizzoGruppo);
            DatagramPacket pacchetto;

            for(int i=0; i<100; i++){
                byte buffer[]= new byte[256];
                pacchetto = new DatagramPacket(buffer, buffer.length);
                client.receive(pacchetto);
                String messaggioRicevuto = new String(pacchetto.getData());
                System.out.println(messaggioRicevuto);
            }

            client.leaveGroup(indirizzoGruppo);
            client.close();

        }catch (Exception e ){
            e.printStackTrace();
        }

    }

}
