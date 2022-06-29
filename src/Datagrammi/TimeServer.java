package Datagrammi;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

public class TimeServer {

    public static void main(String...args){

        //**LATO SERVER**//

        //creo il canale di comunicazione per i datagrammi
        DatagramSocket server = null;

        try{

            server = new DatagramSocket(3575); //instanzio il canale di comunicazione e lo collego alla porta specificata
            int n=0;
            while(n<10) {

                //riceve la richiesta
                byte[] buffer = new byte[256];
                //definisco la grandezza del datagramma che invierò come risposta
                DatagramPacket pacchetto = new DatagramPacket(buffer, buffer.length); //creo un pacchetto che riceve il buffer e ha la lunghezza del buffer
                server.receive(pacchetto); //ricevo il pachetto
                //creo la risposta
                String data = new Date().toString();
                buffer = data.getBytes(); //inserisco i byte nel buffer
                //invio la risposta al client
                //trovo l'inidirizzo IP del client così:
                InetAddress indirizzoClient = pacchetto.getAddress();
                //trovo la porta allo stesso modo
                int port = pacchetto.getPort();
                //creo il pacchetto da inviare
                pacchetto = new DatagramPacket(buffer, buffer.length, indirizzoClient, port);
                server.send(pacchetto);
                n++;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
