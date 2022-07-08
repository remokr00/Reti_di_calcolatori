package Appelli.Appello_29_03_2022.EsercizioSocket;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Client extends Thread{

    private final int port1 = 3000;
    private final int port2 = 4000;
    private Socket client1;
    private MulticastSocket client2;
    private final String ip = "230.0.0.1";


    public Client(){
        try{
            client1 = new Socket("lotteire.unical.it", 3000);
            client2 = new MulticastSocket(4000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        try{
            //Invio la scommessa
            PrintWriter output = new PrintWriter(client1.getOutputStream(), true);
            output.println(new String("12#3"));

            //ricevo i biglietti
            ObjectInputStream input = new ObjectInputStream(client1.getInputStream());
            ArrayList<Biglietto> mieiBiglietti = (ArrayList<Biglietto>) input.readObject(); //non serve  a niente questo array però dalla traccia non si cabiva cosa volesse dire "restituisce una lista di biglietti"

            //attendo la risposta
            client2.joinGroup(InetAddress.getByName(ip));
            byte[] buffer = new byte[256];
            DatagramPacket pacchetto = new DatagramPacket(buffer, buffer.length);
            System.out.println(pacchetto.getData());


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
