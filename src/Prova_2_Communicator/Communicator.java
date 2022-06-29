package Prova_2_Communicator;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class Communicator {

    static int portaMulticast;
    static int socketPort;

    static void InviaMessaggioMulticast(){

        try{
            while(true){
                //invia in multicast un datagramma contente la TCP port (socketPort) usata dall'utente
                byte buffer[] = new byte[256];
                String messaggio = "";
                messaggio += socketPort;
                buffer = messaggio.getBytes();
                InetAddress indidirzzoMultiCast = InetAddress.getByName("230.0.0.1");
                MulticastSocket socketMultiCast = new MulticastSocket();
                DatagramPacket pacchetto = new DatagramPacket(buffer, buffer.length, indidirzzoMultiCast, 2000);
                System.out.println("\nCO>Invio datagramma multicast");
                socketMultiCast.send(pacchetto);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }//InviaMessaggioMulticast

    public static void main(String...args){
        portaMulticast = 2000; //per ricevere i datagrammi multicast
        //leggo la socketPort da tastiera
        Scanner scanner = new Scanner(System.in);
        System.out.println("Porta TCP locale: ");
        socketPort = Integer.parseInt(scanner.next()); //porta TCP scelta dall'utente
        MulticastListener multicastListener = new MulticastListener(portaMulticast, socketPort);
        SocketListener socketListener = new SocketListener(socketPort);
        multicastListener.start();
        socketListener.start();
        InviaMessaggioMulticast();
    }



}
