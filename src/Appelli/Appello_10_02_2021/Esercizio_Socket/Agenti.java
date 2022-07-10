package Appelli.Appello_10_02_2021.Esercizio_Socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.*;

public class Agenti extends Thread{

    private int id;
    private final int port1 = 3000;
    private final int port2 = 6000;
    private final int port3 = 4000;
    private final String ip = "230.0.0.1";

    public Agenti(int id){
        this.id = id;
    }

    public void run(){
        try{
            Socket client = new Socket("server.it", port1);
            //invio ordine
            Ordine ordine = new Ordine(1,1,1,3, "a");
            ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
            output.writeObject(ordine);
            //ricevo risposta
            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String risposta = input.readLine();

            System.out.println(risposta);
            client.close();
            output.close();
            input.close();

            //mi unico al gruppo multicast
            MulticastSocket client2 = new MulticastSocket(port2);
            InetAddress indirizzo = InetAddress.getByName("230.0.0.1");
            client2.joinGroup(indirizzo);
            byte[] buffer = new byte[256];
            DatagramPacket pacchetto = new DatagramPacket(buffer, buffer.length);
            client2.receive(pacchetto);
            System.out.println(pacchetto.getData());

            //invio anche date
            byte[] buffer2 = ("12#13").getBytes();
            DatagramSocket client3 = new DatagramSocket();
            InetAddress server = InetAddress.getByName("server.it");
            DatagramPacket pacchetto2 = new DatagramPacket(buffer2, buffer2.length, server, port3);
            client3.send(pacchetto2);
            //attendo risposta
            DatagramPacket pacchetto3 = new DatagramPacket(buffer2, buffer2.length);
            client3.receive(pacchetto3);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String ... args){
        new Agenti(2).start();
    }
}
