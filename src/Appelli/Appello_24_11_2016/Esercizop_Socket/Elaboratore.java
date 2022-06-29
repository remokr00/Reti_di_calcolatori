package Appelli.Appello_24_11_2016.Esercizop_Socket;

import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Elaboratore {

    /*
    In questo caso l'elaboratore può essere visto come un client
    questo perché all'interno della traccia il scopo è
    solamente quello di inviare un oggetto al gestore
    L'elaboratore però poi non sara più ricontattato

    Logicamente si può pensare che questo possa essere contattato dal client
    una volta che queto ottiene il suo indirizzo, come specificato nella traccia.
    Però non viene menzionato alcuna porta su cui è in ascolto l'elaboratore
    ne che questo verrà prima o poi contattato

    In conclusione, dovendo solo eseguire l'invio di un oggetto verso un'altra
    entità senza poi essere ricontattato può essere implementato come un client
     */
    private OffertaRisorsa offerta; //oggetto da inviare
    private final int serverPortTCP = 3000; //mi serve per instaurare la conessione TCP con gestore
    private Socket elaboatore; //socket per la comunicazione
    InetAddress indirizzoGestore;//mi serve come parametro per inizializzare il socket

    //creo il costruttore
    /*
    Poiché non viene specificato l'inet address del gestore e non
    saprei come prenderlo suppongo questo venga passato come parametro
    al costruttore di elaboratore insieme all'offerta
     */
    public Elaboratore(OffertaRisorsa or, InetAddress indirizzo){
        try {
            elaboatore = new Socket(indirizzo, serverPortTCP);
            offerta = or;
            indirizzoGestore = indirizzo;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
    L'unica cosa che deve fare l'elaboratore è inviare l'offerta
    al gestore
     */
    public void eseguiOfferta(){
        try{
            /*
            Dato che compito è solo inviare l'offerta al gestore ho
            necessita solo di 'printare'
             */
            ObjectOutputStream output = new ObjectOutputStream(elaboatore.getOutputStream());
            System.out.println("Invio l'offerta all'elaboratore...");
            output.writeObject(offerta);
            output.close();
            elaboatore.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }





}
