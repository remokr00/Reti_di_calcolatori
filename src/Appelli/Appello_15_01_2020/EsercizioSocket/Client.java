package Appelli.Appello_15_01_2020.EsercizioSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class Client extends Thread{

    //Variabili
    private final int serverPort = 1111;
    private final String hostName = "gestore.dimes.unical.it";
    private Socket client;
    private ObjectInputStream input;
    private PrintWriter output;
    private List<String> risposte;

    public Client(){
        try {
            client = new Socket(hostName, serverPort);
            input = new ObjectInputStream(client.getInputStream());
            output = new PrintWriter(client.getOutputStream(),true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        try{
            //Invio la richiesta
            String richiesta = "20/10/2022,5";
            System.out.println("Invio la richiesta...");
            output.println(richiesta);
            //Ricevo la risposta
            risposte = (List<String>) input.readObject();
            System.out.println("Ho ricevuto le seguenti offerte: ");
            for(String offerta : risposte){
                System.out.println(offerta);
            }
            output.close();
            input.close();
            client.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String ... args){
        new Client().start();
    }
}
