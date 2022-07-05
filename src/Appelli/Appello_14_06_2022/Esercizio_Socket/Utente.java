package Appelli.Appello_14_06_2022.Esercizio_Socket;

import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

public class Utente extends Thread{

    private final int multicastPort = 6000;
    private final String multicastIP = "230.0.0.1";
    private InetAddress indirizzoMulticast;
    private MulticastSocket clientMulticast;
    private Socket client;
    private final int serverPort = 4000;
    private final String host = "job.unical.it";
    private PrintWriter output;

    public Utente(){
        try{
            client = new Socket(host, serverPort);
            clientMulticast = new MulticastSocket();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        try{
            System.out.println("Mi metto in attesa di offerte di lavoro...");
            indirizzoMulticast = InetAddress.getByName(multicastIP);
            clientMulticast.joinGroup(indirizzoMulticast);
            byte[] buffer = new byte[256];
            DatagramPacket pacchetto = new DatagramPacket(buffer, buffer.length);
            clientMulticast.receive(pacchetto);
            String offerta = new String(pacchetto.getData());
            System.out.println("Ho ricevuto l'offerta: "+offerta);
            String[] parti = offerta.split("#");
            String id = parti[1];

            System.out.println("Invio la candidatura...");
            String candidatura = parti[1]+"#www.mioCurriculum.it";
            output = new PrintWriter(client.getOutputStream(), true);
            output.println(candidatura);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String...args){
        new Utente().start();
    }

}
