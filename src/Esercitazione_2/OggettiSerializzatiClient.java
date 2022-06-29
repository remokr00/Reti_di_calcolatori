package Esercitazione_2;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class OggettiSerializzatiClient {
    public static void main(String...args){
        try{
            Socket socket = new Socket("localhost", 3575); //faccio in modo che server e clent cxomunichino sulla stessa porta
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream()); //usiamo questo stream per leggere  ogetti generici
            /*
            Dobbiamo sapere cosa ci aspettiamo dal server per poter creare l'applicaizone lato client
             */
            String beginMessagge=(String) input.readObject();
            System.out.println(beginMessagge);
            Studente studente = (Studente) input.readObject();
            System.out.println(studente.toString());
            String endMessage = (String) input.readObject();
            System.out.println(endMessage);
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
