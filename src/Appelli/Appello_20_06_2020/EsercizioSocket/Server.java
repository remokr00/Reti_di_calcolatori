package Appelli.Appello_20_06_2020.EsercizioSocket;

import VoliAreoporto_WS.GestisciRichieste;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {

    private final int porta1 = 3000;
    private final int porta2 = 4000;
    private final int porta3 = 5000;
    private final int porta4 = 6000;
    private InetAddress indirizzoCentralina;
    private int portCentralina;
    private ServerSocket server1, server2, server3;
    public HashMap<String, Double[]> database;


    public Server(){
        System.out.println("Inizalizzo il server...");
        database = new HashMap<>();
        avvia();
    }

    private void avvia(){
        try{
            server1 = new ServerSocket(3000);
            server2 = new ServerSocket(4000);
            server3 = new ServerSocket(5000);
            //Avvio tutte le possibili connessioni
            Socket client = server1.accept();
            new GestisciRichiesta1(client).start();
            Socket client2 = server2.accept();
            new GestisciRichiesta2(client2).start();
            Socket centralina = server3.accept();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class GestisciRichiesta1 extends Thread{

        private Socket client;

        public GestisciRichiesta1(Socket c){
            client = c;
        }

        public void run(){
            try{
                //ricevo un id da parte del client
                BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String richiesta = input.readLine();
                String[] parti = richiesta.split("#");
                String id = parti[0];
                String grandezza = parti[1];

                //trovo il valore della grandezza che sto cercando
                boolean trovato =false;
                double valore = 0;
                ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
                for(Map.Entry<String,Double[]> entry: database.entrySet()){
                    if(id.equals(entry.getKey())){
                        trovato =true;
                        if(grandezza.equals("Temperatura")) valore = entry.getValue()[0];
                        if(grandezza.equals("Umidità")) valore = entry.getValue()[1];
                        if(grandezza.equals("Pressione Atmosferica")) valore = entry.getValue()[2];
                    }
                }
                if(!trovato){
                    System.out.println("Centralina non registrata");
                    output.writeObject(null);
                }
                else {
                    Dato dato = new Dato(grandezza, valore );

                    //invio il dato
                    output.writeObject(dato);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        class GestisciRichiesta2 extends Thread{

            private Socket client;

            public GestisciRichiesta2(Socket c){
                client = c;
            }

            public void run(){
                try{
                    //TODO 
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

}
