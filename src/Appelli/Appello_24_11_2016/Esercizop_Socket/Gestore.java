package Appelli.Appello_24_11_2016.Esercizop_Socket;

import EserciziPerIlaria.Es1TCP.Server;

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Gestore {

    /*
    Entità più complicata poiché funge sia da client che da server
    Iniziamo dalle variabili
     */

    /*
    Dato che devo salavre le offerte le devo salvare all'interno di una
    struttura dati apposita. Uso hashMap per creare una corrispondenza
    tra indirizzo dell'elaboratore e offerta corrispondente. Ritengo sia
    la maniera più semplice
     */
    private HashMap<InetAddress, OffertaRisorsa> database;
    private final int portaPerElaboratori = 3000; //porta su cui ascolta il primo ServerSocket
    private final int portaPerClient = 2000; //porta su cui ascolta il secondo ServerSocket
    private Socket elaboratore;
    private Socket client;
    private ServerSocket serverSocket1;
    private ServerSocket serverSocket2;

    /*
    Ho cercato di creare tutte le variabili possibili per evitare di doverle passare come parametri ai vari thread e metodi
    in questo modo le ho rese visibili a tutte le classi di questo file
     */

    /*
    Il costruttore è abbastanza articolato in quanto per il client devo gestire connessioni
    multiple mentre per gli elaboratori no. Andiamo per gradi
     */

    //non ho necessità di inviare niente al costruttore come parametro
    //poiché dispongo di tutte le informazioni necessarie alla comunicazione
    public Gestore(){
        //demando l'inizializzazione del server a due metodi a parte per
        //mantenere la modularità
        System.out.println("Inizializzo il gestore...");
        //inizializzo il database
        database = new HashMap<>();
        avviaServerPerElaboratori();
        avviaServerPerClient();
    }

    /*
    Questo metodo serve per gestire le connessioni degli elaboratori
     */
    private void avviaServerPerElaboratori(){
        try {
            //Inizializzo il server socket per gli elaboratori
            serverSocket1 = new ServerSocket(portaPerElaboratori);
            //non devo gestire connessioni multiple in questo caso quindi
            //mi basta fare:
            elaboratore = serverSocket1.accept();
            //avvio i thread per la gestione degli elaboratori
            new ThreadElaboratori().start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
    Questo metodo invece serve per gestire le connessioni multiple dei client
     */
    public void avviaServerPerClient(){
        try {
            //passo 1. Inizializzo il serverSocket
            serverSocket2 = new ServerSocket(portaPerClient);
            /*
            passo 2. Avvio il thread per le gestioni delle connessioni multiple
             */
            new AvviaConnessioni().start();
            /*
            Per vedere passo 3 verificare la classe avviaConnessioni.
             */
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /*
    La seguente inner class che estende thread serve unicamente per
    avviare le connesisoni multiple, si riprende dal passo 3
     */
    class AvviaConnessioni extends Thread{
        /*
        passo 3.
        Creo il metodo run per l'avvio di connessioni multiple
         */
        public void run(){
            try{
                //passo 4. accetto connesisoni in un while true
                while (true){
                    client = serverSocket1.accept();
                    //passo 5.
                    /*
                    Per gestire in maniera simultanea i client lancio il thread
                     */
                    new GestisciClient().start();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /*
    Di seguito la classe thread che gestisce la ricezione delle offerte
    da parte degli elaboratori
     */
    class ThreadElaboratori extends Thread{

        /*
        Creo ora il metodo run per la gestione delle operazioni
         */
        public void run(){
            try {
                /*
                1. Ricevo l'offerta
                 */
                ObjectInputStream input = new ObjectInputStream(elaboratore.getInputStream());
                /*
                2. salvo l'offerta nel database
                2.1 Ricavo l'inetAddress dell'elaboratore
                 */
                InetAddress indirizzoElaboratore = elaboratore.getInetAddress();
                /*
                2.2 prendo l'offerta di risorsa
                 */
                OffertaRisorsa offertaRisorsa = (OffertaRisorsa) input.readObject();
                database.put(indirizzoElaboratore,offertaRisorsa);
                /*
                3 l'elaboratore ha finito chiudo tutto
                 */
                input.close();
                elaboratore.close();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /*
    La seguente classe serve per gestire le richieste del client
     */
    class GestisciClient extends Thread{

        /*
        Nel metodo run eseguo le richieste
         */
        public void run(){
            try{
                /*
                1. Leggo la risorsa che richiede il client
                 */
                ObjectInputStream input = new ObjectInputStream(client.getInputStream());
                RichiestaRisorsa richiestaRisorsa = (RichiestaRisorsa) input.readObject();

                /*
                2. Verifico se è presente nel databse vedendo se tipo e descrizione sono uguali

                Creo un for each in cui analizzo ogni coppia chiave valore
                 */
                InetAddress risposta = null; //inet address dell'eleaboratore che sto cercando
                boolean trovato = false; //per verificare l'assenza della risorsa
                for(Map.Entry<InetAddress, OffertaRisorsa> entry : database.entrySet()){
                    OffertaRisorsa offertaCorrente = entry.getValue();
                    if( richiestaRisorsa.getTipo().equals(offertaCorrente.getTipo()) && richiestaRisorsa.getDescrizione().equals(offertaCorrente.getDescrizione())){
                        risposta = entry.getKey(); //devo restituire l'inet address dell'elaboratore al quale corrisponde la risorsa che sto cercando
                        trovato = true;
                        break; //dato che devo restituire il primo indirizzo esco dal ciclo in maniera forzata
                    }
                }
                //creo l'output stream per comunicare col client
                PrintWriter output = new PrintWriter(client.getOutputStream(), true);
                if(!trovato){
                    //non viene specificato cosa inviare ma io restituisco comunque
                    //un messaggio di errore
                    output.println("Non è stata trovata alcun elaboratore con la risorsa richiesta");
                }
                output.println(""+risposta);

            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }

}
