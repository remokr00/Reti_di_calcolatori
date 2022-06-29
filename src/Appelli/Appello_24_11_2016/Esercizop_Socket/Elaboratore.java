package Appelli.Appello_24_11_2016.Esercizop_Socket;

import java.net.ServerSocket;
import java.net.Socket;

public class Elaboratore {

    //richiesta connessioni multiple
    public Elaboratore(){
        ServerSocket s = new ServerSocket();
        Socket client s.accept();
        new threadRichiesta().start();
        new threadRisposta().start();
    }

    
}
