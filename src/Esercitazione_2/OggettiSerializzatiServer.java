package Esercitazione_2;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class OggettiSerializzatiServer {

    public static void main(String...args){
        /*
        La serializzazione è un processo per salvare un oggetto in un supporto
        di memorizzazione o per trasmetterlo su una connessione di rete
         */
        try{
            ServerSocket server = new ServerSocket(3575); //il server è in ascolto sulla porta indicata
            Socket client = server.accept(); //attendo che si instauri una connessione con il client
            ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream()); //questo output stream serve per stamapare oggetti generici
            output.writeObject("<Welcome>");
            Studente studente = new Studente (14520, "Leonardo", "da Vinci", "Ingegneria Informatica");
            output.writeObject(studente);
            output.writeObject("<Goodbye>");
            client.close();
            server.close();
        }catch (Exception e ){
            e.printStackTrace();
        }
    }
}
