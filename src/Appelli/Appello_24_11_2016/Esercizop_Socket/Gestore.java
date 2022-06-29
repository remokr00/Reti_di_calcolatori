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
    private ServerSocket serverSocket1;
    private ServerSocket serverSocket2;

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
            Socket elaboratore = serverSocket1.accept();
            //avvio i thread per la gestione degli elaboratori
            /*
            Invio il database al thread perché, trovandosi in un'altra
            classe questo poi non sarebbe visibile per l'aggiornamento.

            Invio il socket elaboratore altrimenti non potrei leggere dal socket
            sempre perché si trova in una classe differente
             */
            new ThreadElaboratori(database, elaboratore).start();
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
            passo 2. Poiché devo gestire le conessioni multiple devo demandare
            l'instaurazione delle connessioni ad un thread a parte. A questo
            thread passo il serverSocket due per poter poi utilizzare il metodo
            accept
             */
            new AvviaConnessioni(serverSocket2).start();
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

        //Passo 3.
        //creo come variabile di istanza il
        //server socket per avviare le connessioni multiple
        private ServerSocket serverSocket1;

        //passo 4. inizializzo il costruttore
        public AvviaConnessioni(ServerSocket s){
            try{
                serverSocket1 = s;
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        /*
        passo 5.
        Creo il metodo run per l'avvio di connessioni multiple
         */
        public void run(){
            try{
                //passo 6 accetto connesisoni in un while true
                while (true){
                    Socket client = serverSocket1.accept();
                    //passo 7.
                    /*
                    Per gestire in maniera simultanea i client lancio i vari thread
                    ai quali passo il socket perché trovandosi in classi diverse
                    non sarei ingrado di scrivere e/o leggere dagli stream

                    Passo anche il database perché altrimenti, essendo in un altra classe
                    non lo potrei consultare
                     */
                    new GestisciClient(database, client).start();
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

        //creo una variabile di istanza per mantenere il database
        private HashMap<InetAddress, OffertaRisorsa> databse;
        private Socket elaboratore;

        /*
        Avendo passato un parametro al thread creo il costruttore
         */
        public ThreadElaboratori(HashMap<InetAddress, OffertaRisorsa> d, Socket e){
            database = d;
            elaboratore = e;
        }

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

        //creo la variabile client
        private Socket client;
        private HashMap<InetAddress, OffertaRisorsa> database;

        public GestisciClient(Socket c, HashMap<InetAddress, OffertaRisorsa> d){
            client = c;
            database = d;
        }

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
