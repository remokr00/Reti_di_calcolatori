package Multicast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Date;

public class MulticastTimeServer {

    public static void main(String...args){

        MulticastSocket server = null;

        try{

            server = new MulticastSocket(3576);

            while(true){

                byte[] buffer = new byte[256];

                //in questo tipo di comunciazione non si attende la rihciesta da parte del client
                //si inviano direttamente i pachetti in broadcast ogni qual volta il clienti si unisce al gruppo
                //multicast

                String data = new Date().toString();
                buffer = data.getBytes();

                //invio il messaggio in broadcast

                InetAddress indirizzoGruppo = InetAddress.getByName("230.0.0.1"); //indirizzo preso dalle slide
                //CREO IL PACCHETTO DI INFORMAZIONI DA INVIARE
                DatagramPacket pacchetto = new DatagramPacket(buffer, buffer.length, indirizzoGruppo, 3575);
                server.send(pacchetto);
                //Stampo su output cosa sto inviando per debugging
                System.out.println("Broadcasting: "+data);
                Thread.sleep(1000);


            }

        }catch (Exception e){
            e.printStackTrace();
            server.close();
        }
    }

}
