package Appelli.Appello_15_01_2020.EsercizioSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Gestore {

    private final int clientPort = 1111;
    private final int centroPort = 2222;
    private final int multicastPort = 3333;
    private final String multicastIp = "224.3.2.1";
    private ServerSocket gestoreClient;
    private ServerSocket gestoreCentro;
    private Socket centro;
    private MulticastSocket multicastCentro;
    private List<String> offerte;
    private BufferedReader inputClient;
    private InetAddress indirizzoMulticast;
    private BufferedReader inputCentro;
    private ObjectOutputStream outputClient;


    public Gestore(){
        try {
            gestoreClient = new ServerSocket(clientPort);
            gestoreCentro = new ServerSocket(centroPort);
            offerte = Collections.synchronizedList(new LinkedList<String>());
            System.out.println("Avvio il server...");
            avvia();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void avvia(){
        new GestisciConnessioni().start();
        new GestisciCentro().start();
    }

    class GestisciConnessioni extends Thread{

        public void run(){
            try {
                while (true) {
                    Socket client = gestoreClient.accept();
                    new GestisciRichiestaClient(client).start();
                    new GestisciRispostaClient(client).start();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class GestisciRichiestaClient extends Thread{

        private Socket client;

        public GestisciRichiestaClient(Socket c){
            client = c;
        }

        public void run(){
            try {
                //ricevo la richiesta da parte del client
                inputClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String richiesta = inputClient.readLine();
                System.out.println("Inoltro la richiesta ai centri...");
                inputClient.close();
                //inoltro la richiesta in multicast
                multicastCentro = new MulticastSocket(multicastPort);
                byte[] buffer = richiesta.getBytes();
                indirizzoMulticast = InetAddress.getByName(multicastIp);
                DatagramPacket pacchetto = new DatagramPacket(buffer, buffer.length, indirizzoMulticast,multicastPort);
                multicastCentro.send(pacchetto);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class GestisciRispostaClient extends Thread{

        private Socket client;

        public GestisciRispostaClient(Socket c){
            client = c;
        }
        public void run(){
            try{
                //invio al client la lista
                synchronized (offerte){
                    System.out.println("Invio la lista di offerte al client...");
                    outputClient = new ObjectOutputStream(client.getOutputStream());
                    outputClient.writeObject(offerte);
                    outputClient.close();
                    client.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class GestisciCentro extends Thread{

        public void run() {
            try {
                centro = gestoreCentro.accept();
                gestoreCentro.setSoTimeout(60000);
                //ricevo l'offerta e l'aggiungo alla lista
                synchronized (offerte) {
                    String offerta = inputCentro.readLine();
                    offerte.add(offerta);
                }
            } catch(Exception e){
                    System.out.println("Tempo scaduto");

            }
        }

    }

    public static void main(String ... args){
        Gestore g = new Gestore();
    }



}
