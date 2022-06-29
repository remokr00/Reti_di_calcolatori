package Prova_2_Communicator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketListener extends Thread{

    int portaTCP; //porta su cui il socket listener rimane in ascolto per i messaggi TCP
    InetAddress indirizzoRemoto;
    int portaTCPremota; //porta su cui il communicator che ha risposto è in ascolto per ricevere i messaggi TCP

    public SocketListener(int portaTCP){
        this.portaTCP=portaTCP;
        System.out.println("SL> Porta TCP locale = "+portaTCP);
    }

    public void run(){

        try{
            ServerSocket serverListener = new ServerSocket(portaTCP);
            while (true){
                //ricevo messaggio di risposta via TCP
                Socket client = serverListener.accept();
                indirizzoRemoto = client.getInetAddress();
                BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String linea = input.readLine();
                //il mesaggio di risposta contiene la porta TCP del communicator che ha risposto e che viene
                //usata per ricevere a sua volta messaggi TCP
                portaTCPremota = Integer.parseInt(linea);
                System.out.println("\nSL>Ricevuta risposta da "+ indirizzoRemoto.getHostAddress()+":"+client.getPort());
                System.out.println("SL>Stringa socket ricevuta: "+ portaTCPremota);
                client.close();
            }//while
        } catch (Exception ex) { System.out.println("SL>"+ex);}

    }

}
