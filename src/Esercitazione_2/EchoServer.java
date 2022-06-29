package Esercitazione_2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    /*
    Un echo server riceve un messaggio e restituisce lo stesso messaggio
     */
    public static void main(String...args){
        try{
            ServerSocket s = new ServerSocket(8190); //creo il server socket e lo metto in ascolto su una determinata porta
            Socket incoming = s.accept(); //l'app si ferma in attesa di una connessione
            BufferedReader in = new BufferedReader(new InputStreamReader(incoming.getInputStream())); //leggo cosa mi invia il client tramite il buffered reader
            PrintWriter out = new PrintWriter(incoming.getOutputStream(), true /*autoFlush*/); //uso il printWriter per scrivere i messaggi sul socket
            out.println("Hello! Enter BYE to exit");
        //creo un cilo per vedere se ho inserito la parola BYE per concludere la connessione
            boolean done=false;
            while(!done){
                String line=in.readLine(); //chiamata bloccante che mi consente di leggere una linea di carattere alla volta dal mio input stream
                if(line==null) done=true;
                else{
                    out.println("Echo: "+line);
                    if(line.trim().equals("BYE")) done=true;
                }
            }
            incoming.close();
        }catch (Exception e){e.printStackTrace();}
    }
}
