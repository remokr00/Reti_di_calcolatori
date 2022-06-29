package Appelli.Appello_24_11_2016.Esercizop_Socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    /*
    Creo il client come una classe vera e propria con variabili di istanza
    anche perché non è specificato che ci debba essere un main
     */
    private final int serverPortTCP = 2000; //mi serve per inizializzare il socket
    private Socket client; //canale di comunicazione
    private InetAddress indirizzoGestore; //mi serve per inizializzare il socket
    private RichiestaRisorsa richiesta; //oggetto da scambiare

    /*
    Non sapendo come prelevare l'InetAddress del gestore non essendo specificato
    suppongo questo venga passato come parametro per il costruttore
     */
    public Client(InetAddress indirizzo, RichiestaRisorsa rr){
        try {
            client = new Socket(indirizzo, serverPortTCP);
            indirizzoGestore = indirizzo;
            richiesta = rr;
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /*
    creo un metodo per la gestione della lettura e scrittura sul client
     */

    public void richiedi(){
        try {
            /*
            Devo inviare un oggetto al gestore
             */
            ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
            System.out.println("Invio la richiesta al gestore");
            output.writeObject(richiesta);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
    Volendo richiedi e riceviRisposta potevano essere implementati nello stesso
    metodo
     */
    public void riceviRisposta(){
        try{
            /*
            Ricevo una risposta da parte del gestore, dato che ricevo
            l'indirizzo dell'elaboratore che contiene la risorsa che mi
            interessa posso utilizzae un semplice bufferedReader
             */
            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String risposta = input.readLine();
            System.out.println("L'indirizzo dell'eleaboratore che contiene la mia risorsa è: "+risposta);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
