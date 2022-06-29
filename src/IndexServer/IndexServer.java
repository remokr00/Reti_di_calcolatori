package IndexServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class IndexServer {

    private Map<File, InetAddress> data;

    private final int portaUDP = 3000; //riceve da un generico StorageServer x informazioni sui file memorizzati
    private final int portaTCP = 4000; //riceve la richiesta di un client per la ricerca di un file

    private ServerSocket serverTCP;
    private DatagramSocket serverUDP;

    public IndexServer(){

        //utilizzo una struttura dati thread-safe
        data = Collections.synchronizedMap(new HashMap<File, InetAddress>());
        System.out.println("Server in fase di avvio");
        inizia();

    }//costruttore

    private void inizia(){

        try{
            serverTCP = new ServerSocket(portaTCP);
            System.out.println(serverTCP);
            serverUDP = new DatagramSocket(portaUDP);
        }catch (Exception e){
            e.printStackTrace();
        }

        //richiamo i thread per la gestione delle richieste
        new GestisciFile().start();
        new GestisciClient().start();

    }//inizia

    class GestisciFile extends Thread{

        public void run(){

            try{
                //ricevo informazioni dallo StorageServer
                while (true){
                    byte buffer[] = new byte[256];
                    //Devo creare il pacchetto dato che la comunicaizone è di tipo UDP
                    DatagramPacket pacchetto = new DatagramPacket(buffer, buffer.length);
                    System.out.println("Attesa nuovo pacchetto...");
                    serverUDP.receive(pacchetto);

                    //Stampo cio che mi è arrivato
                    String messaggio = new String (pacchetto.getData());
                    System.out.println(messaggio);

                    //divido il messaggio
                    String[] parti = messaggio.split("#");
                    //divido ora le parole chiave
                    String[] paroleChiave = parti[1].split(",");

                    //creo il file
                    File file = new File(parti[0], paroleChiave, "");
                    System.out.format("Aggiunto file %s inviato da %s%n", file.getFileName(), pacchetto.getAddress());

                    //inserisco il file nella lista
                    data.put(file, pacchetto.getAddress());

                }//while
            }catch (Exception e){
                e.printStackTrace();
            }//try-catch

        }//run

    }//GestisciFile

    class GestisciClient extends Thread{

        public void run(){

            try{

                //accetto le connessioni da parte dei client
                Socket client = serverTCP.accept();

                //leggo cosa il client mi chiede di ricercare
                BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));

                //leggo il contenuto
                String richiesta = input.readLine();
                System.out.println("Il client vuole cercare il file: "+ richiesta);

                //divido il messaggio
                String[] parti = richiesta.split("#");
                String[] paroleChiave = parti[1].split(",");
                String nome = parti[0];

                //cerco il file all'interno della mappa e creo il risultato
                String risultato = "";

                synchronized (data){
                    for(Map.Entry<File, InetAddress> entry : data.entrySet()){
                        File file = entry.getKey();

                        if(file.getFileName().equals(nome)) {
                            boolean trovato = true;
                            for(int i=0; i<paroleChiave.length && trovato; i++){
                                trovato = false;
                                for(int j=0; j<file.getKeyWords().length; j++){
                                    if(paroleChiave[i].equals(file.getKeyWords()[j])){ //sbagliato(?)
                                        trovato = true;
                                        break;
                                    }
                                }
                            }
                            if(trovato) risultato+=entry.getValue().toString();
                        }
                    }
                }//synchronized

                //rispondo al client
                System.out.println("Ecco la lista di storage che presentano i file richiesti: "+risultato);
                PrintWriter output = new PrintWriter(client.getOutputStream());
                output.println(risultato);

                //chiudo tutto
                output.close();
                input.close();
                client.close();

            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }//GestisciClent

    public static void main(String...args){IndexServer is = new IndexServer();}



}//IndexServer
