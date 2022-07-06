package Appelli.Appello_11_11_2020.Esercizo_Socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

public class Client extends Thread{

    private final int serverPort = 1111;
    private final String host = "analisi.dimes.it";
    private final int mCast = 4000;
    private final String ip = "230.0.0.1";
    private Socket client;
    private MulticastSocket client2;

    public Client(){
        try{
            client = new Socket(host, serverPort);
            client2 = new MulticastSocket(mCast);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        try{
            //ricevo il messaggio in multicast
            InetAddress indirizzo = InetAddress.getByName(ip);
            client2.joinGroup(indirizzo);
            byte[] buffer = new byte[256];
            DatagramPacket pacchetto = new DatagramPacket(buffer, buffer.length);
            client2.receive(pacchetto);
            String media = new String(pacchetto.getData());
            System.out.println("Ho ricevuto :" + media);

            //invio le analisi
            String analisi = "<Analisi#2.0#3.0>";
            PrintWriter output = new PrintWriter(client.getOutputStream(), true);
            output.println(analisi);

            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String risposta = input.readLine();
            System.out.println("Ho ricevuto: "+risposta);

            output.close();
            input.close();
            client2.leaveGroup(indirizzo);
            client2.close();
            client.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String...args){
        new Client().start();
    }
}
