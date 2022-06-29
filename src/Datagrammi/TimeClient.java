package Datagrammi;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TimeClient {

    public static void main(String...args){

        try {

            //nome della macchina
            String hostName = "localhost";
            //creo il canale di comunicaone
            DatagramSocket client = new DatagramSocket();
            //invio la richiesta
            //1. creo il buffer
            byte[] buffer = new byte[256];
            //2. prendo l'inidirizzo del localhost
            InetAddress indirizzoMacchinaLocale = InetAddress.getByName(hostName);
            //3. creo il pacchetto e lo invio
            DatagramPacket pacchetto = new DatagramPacket(buffer, buffer.length, indirizzoMacchinaLocale, 3575);
            client.send(pacchetto);
            //ricevo la risposta
            pacchetto = new DatagramPacket(buffer, buffer.length);
            client.receive(pacchetto);
            //visualizzo la risposta
            String risposta = new String(pacchetto.getData()); //estraggo i dati dal pacchetto e li stampo
            System.out.println("Risposta: "+ risposta);
            client.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
