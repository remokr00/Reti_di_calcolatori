package Appelli.Appello_14_06_2022.Esercizio_Socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.*;
import java.sql.Time;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Server {

    private final int myPort1 = 3000;
    private final int myPort2 = 4000;
    private final int myPort3 = 5000;
    private final int myMcastPort = 6000;
    private final String mCastIP = "230.0.0.1";
    private InetAddress indirizzoMCast;
    private ServerSocket server1;
    private ServerSocket server2;
    private Socket server3;
    private MulticastSocket mCastSocket;
    private HashMap<Integer, InetAddress> database; //Ad ogni codice id associo l'azienda che ha proposto l'fferta
    private ObjectInputStream inputAzienda;
    private PrintWriter outputAzienda;
    private BufferedReader inputClient;
    private Offerta offerta;

    public Server(){
        try {
            System.out.println("Inizializzo il server");
            database = (HashMap<Integer, InetAddress>) Collections.synchronizedMap(new HashMap<Integer, InetAddress>());
            new GestisciAzienda().start();
            new GestisciClient().start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class GestisciAzienda extends Thread{

        public void run(){
            try{
                System.out.println("Ricevo offerte dall'azienda...");
                server1 = new ServerSocket(myPort1);
                Socket azienda = server1.accept();
                inputAzienda = new ObjectInputStream(azienda.getInputStream());
                offerta = (Offerta) inputAzienda.readObject();
                System.out.println("Calcolo l'id da assegnare all'offerta...");
                int id = database.size()*((int)Math.random()*10000+1);
                System.out.println("Ho ricevuto l'offerta ora la inserisco nel database...");
                InetAddress indirizzoAzienda = azienda.getInetAddress();
                synchronized (database){
                   database.put(id, indirizzoAzienda);
                }
                System.out.println("L'offerta scade tra 30 giorni");

                new Scadenza(id).start();

                System.out.println("Invio l'offerta a tutti gli utenti...");
                mCastSocket = new MulticastSocket(myMcastPort);
                String messaggio = ""+id+"#"+offerta.getSettore()+"#"+offerta.getTipo()+"#"+offerta.getRetribuzione();
                byte[] buffer = messaggio.getBytes();
                DatagramPacket pacchetto = new DatagramPacket(buffer, buffer.length, indirizzoMCast, myMcastPort);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    class Scadenza extends Thread{

        private int id;

        public Scadenza(int id){
            this.id = id;
        }

        public void run(){
            try{
                TimeUnit.DAYS.sleep(30);
                System.out.println("L'offerta "+ id +" è scaduta");
                synchronized (database){
                    database.remove(id);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class GestisciClient extends Thread{

        public void run(){
            try{
                System.out.println("Ricevo candidature dai client...");
                server2 = new ServerSocket(myPort2);
                Socket client = server2.accept();
                inputClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String candidatura = inputClient.readLine();
                String[] parti = candidatura.split("#");
                String id = parti[1];
                System.out.println("Controllo se l'offerta è ancora valida...");
                synchronized (database) {
                    if (database.containsKey(Integer.parseInt(id))) {

                        System.out.println("Invio l'offerta all'azienda...");
                        server3 = new Socket(database.get(id), myPort3);
                        outputAzienda = new PrintWriter(server3.getOutputStream());
                        outputAzienda.println(candidatura);

                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    public static void main(String ... args){
        Server s = new Server();
    }

}
