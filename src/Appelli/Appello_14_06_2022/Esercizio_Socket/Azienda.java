package Appelli.Appello_14_06_2022.Esercizio_Socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Azienda extends Thread{

    private final int serverPort1 = 3000;
    private final int serverPort2 = 5000;
    private final String host = "job.unical.it";
    private Socket azienda1;
    private ServerSocket azienda2;
    private ObjectOutputStream output;
    private BufferedReader input1;
    private BufferedReader input2;
    private Offerta offerta;
    private int id;

    public Azienda(){
        try{
            azienda1 = new Socket(host, serverPort1);
            azienda2 = new Socket(host, serverPort2);
             }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        try{
           //Invio un'offerta di lavoro
            System.out.println(("Invio l'offerta di lavoro..."));
            output = new ObjectOutputStream(azienda1.getOutputStream());
            offerta = new Offerta("S1", "R1", "Tempo determinato", "2000€");
            output.writeObject(offerta);

            //Leggo l'id che mi viene assegnato
            input1 = new BufferedReader(new InputStreamReader(azienda1.getInputStream()));
            id = Integer.parseInt(input1.readLine());
            System.out.println("Ho ricevuto l'id della mia offerta.");

            //Rimango in attesa di eventuali offerte di lavoro
            azienda2 = new ServerSocket(serverPort2);
            Socket server = azienda2.accept();
            input2 = new BufferedReader(new InputStreamReader(server.getInputStream()));
            String candidatura = input2.readLine();
            System.out.println("Ho ricevuto la seguente candidatura: "+candidatura);

            azienda1.close();
            azienda2.close();
            input1.close();
            input2.close();
            output.close();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String ... args){
        new Azienda().start();
    }
}
