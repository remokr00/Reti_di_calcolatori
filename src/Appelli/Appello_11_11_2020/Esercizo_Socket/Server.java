package Appelli.Appello_11_11_2020.Esercizo_Socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;


public class Server {

    private ServerSocket server1; //connessioni multiple
    private final int serverPort = 1111;
    private double x;
    private LinkedList<Double> datiRicevuti;
    private int contatoreClient;
    private final String ip = "230.0.0.1";
    private final int mCastPort = 4000;
    private MulticastSocket server2;

    public Server(){
        System.out.println("Inzializzo il server...");
        datiRicevuti = (LinkedList<Double>) Collections.synchronizedList(new LinkedList<Double>());
        contatoreClient = 0;
        try{
            server1 = new ServerSocket(serverPort);
            server2 = new MulticastSocket(mCastPort);
        }catch (Exception e){
            e.printStackTrace();
        }
        new GestisciConnessioni().start();
        new MessaggioMulticast().start();
    }

    class GestisciConnessioni extends Thread{

        public void run(){
            try{
                while(true){
                    Socket client = server1.accept();
                    new GestisciRichiesta(client).start();
                    contatoreClient++;
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class GestisciRichiesta extends Thread{

        private Socket client;

        public GestisciRichiesta(Socket c){
            client = c;
        }

        public void run(){
            try{
                //leggo l'analisi
                BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String analisi = input.readLine();
                String[] parti = analisi.split("#");
                String param1 = parti[1];
                String param2 = parti[2];
                //aggiungo i parametri alla lista per poter poi calcolare la media
                synchronized (datiRicevuti) {
                    datiRicevuti.add(Double.parseDouble(param1));
                    datiRicevuti.add(Double.parseDouble((param2)));
                }
                //calcolo i dati
                double x = Math.sqrt(Double.parseDouble(param1));
                double y = Double.parseDouble(param2)*Double.parseDouble(param2);
                //mi fermo per un tempo random
                double tempo = (Math.random()*10)+1;
                Thread.sleep((long)tempo);
                //verifico se sto effettuando 20 analisi
                PrintWriter output = new PrintWriter(client.getOutputStream(), true);
                String risposta = "<"+x+","+y+">";
                if(contatoreClient>20){
                    output.println("<-1,-1>");
                }
                if(contatoreClient<=20){
                    output.println(risposta);
                }
                //diminuisco il numero di clienti che sto servendo
                contatoreClient--;
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    class MessaggioMulticast extends Thread{

        public void run(){
            try{

                while (true){ // ogni 10 minuti devo inviare la media dei parametri ricevuti quindi metto il while true, devo farlo sempre in pratica
                    double media = 0;
                    double somma = 0;
                    synchronized (datiRicevuti){
                        for(Double num : datiRicevuti){
                            somma += num;
                        }
                        media = somma/datiRicevuti.size();
                    }
                    //creo il pacchetto da inviare
                    byte[] buffer = ("Media : "+media).getBytes();
                    InetAddress indirizzo = InetAddress.getByName(ip);
                    DatagramPacket pacchetto = new DatagramPacket(buffer, buffer.length,indirizzo, mCastPort);
                    server2.send(pacchetto);
                    //ora mi metto a dormire per 10 minut
                    TimeUnit.MINUTES.sleep(10);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String...args){
        Server s = new Server();
    }
}
