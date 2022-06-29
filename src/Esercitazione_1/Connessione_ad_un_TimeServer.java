package Esercitazione_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class Connessione_ad_un_TimeServer {

    /*
    Questa applicazione si connette ad un time server. Ovvero un server che fornisce a tutti
    i client che lo richiedono la data e l'ora corrente. Questi server sono utili per sincornizzare
    i servizi di rete
     */

    public static void main(String[] args){

        try{
            Socket s= new Socket("ntp1.inrim.it", 13); //creo il socket e inserisco come parametri l'host a cui mi voglio collegare e la porta da usare per la comunicazione
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream())); //creo il buffered reader per vedere cosa mi risponde il server
            /*
            Creo un ciclo per poter leggere, nel caso ce ne fosse più di una,
            tutte le righe dalle quali è formata la risposta del server
             */
            boolean more = true;
            while(more){
                String line= in.readLine();
                if(line==null) more=false;
                else System.out.println(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
