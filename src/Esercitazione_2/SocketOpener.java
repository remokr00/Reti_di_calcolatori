package Esercitazione_2;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketOpener {

    /* Il socket opener è un piccolo stratagemma per far si che l'instaurazione di un  socket
    non blocchi per sempre l'esecuzione ma la blocchi per un determinato lasso di tempo.
     */

    public static void main(String...args){

        String host = "www.dimes.unical.it";
        int port = 80; //per far scadere il timeout provare porta 11587
        int timeout= 3000; //imposto il timeout di 3 secondi
        Socket s = SocketOpenerTest.openSocket(host, port, timeout); //creo il socket per il collegamento all'host sulla porta indicata. la creazione del socket è delgata alla inner class SocketOpenerTest
        if (s == null) System.out.println("Il socket non si è aperto");
        else System.out.println(s);
    }

    static class SocketOpenerTest extends Thread{

        private String host;
        private Socket socket;
        private int port;

        /*
        Creo il costruttore della inner class
         */
        public SocketOpenerTest(String host, int port){
            this.host=host;
            this.port=port;
            socket=null; //inizialmente posto uguale a null perché dobbiamo crearlo col metodo openSocket
        }

        public static Socket openSocket(String host, int port, int timeout){
            SocketOpenerTest opener = new SocketOpenerTest(host, port); //creo l'oggetto socket
            opener.start(); //avvio il thread
            try {
                opener.join(timeout); //faccio in modo che il thread aspetti al massimo un tempo pari al timeout
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return opener.getSocket(); //se sono arrivato fino a qui vuol dire che ho ricevuto una risposta entro 3 secondi
        }

        public Socket getSocket(){ return socket;}

        public void run(){
            try{
                socket = new Socket(host, port); //il metodo run crea il socket verso l'host richiesto
            }catch (IOException e){
                e.printStackTrace();
            }

        }



    }
}
