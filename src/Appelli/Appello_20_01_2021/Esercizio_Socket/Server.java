package Appelli.Appello_20_01_2021.Esercizio_Socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class Server {

    private final int porta1 = 3000;
    private ServerSocket server;
    private DatagramSocket server2;
    private MulticastSocket server3;
    private HashMap<Integer, Misura> databse;
    private final int porta2 = 4000;
    private final int porta3 = 5000;
    private final String indirizzo = "230.0.0.1";

    public Server(){
        System.out.println("Avvio il server...");
        databse = new HashMap<>();
        //creo un thread per le connessioni multiple
        new GestisciConnessioni().start();
    }

    class GestisciConnessioni extends Thread{

        public void run(){
            try{
                server = new ServerSocket(porta1);
                server2 = new DatagramSocket(porta2);
                server3 = new MulticastSocket(porta3);
                new GestisciSensori().start();
                new Controllo().start();
                while (true){
                    Socket client = server.accept();
                    new GestisciClient(client).start();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class GestisciClient extends Thread{

        private Socket client;

        public GestisciClient(Socket c){
            client = c;
        }

        public void run(){
            try {
                //1. ricevo dal client una stringa
                BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String id = input.readLine();
                Misura misura = databse.get(Integer.parseInt(id));

                //2. restituisco l'oggetto al client
                ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
                output.writeObject(misura);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class GestisciSensori extends Thread{

        public void run(){
            try{
                //ricevo l'oggetto misura
                byte[] buffer = new byte[256];
                DatagramPacket pacchetto = new DatagramPacket(buffer, buffer.length);
                server2.receive(pacchetto);
                String messaggio = new String(pacchetto.getData());
                String[] parti = messaggio.split("#");
                String id  = parti[0];
                String valore = parti[1];
                Misura misura = new Misura(Integer.parseInt(id), Double.parseDouble(valore));
                databse.put(Integer.parseInt(id),misura);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class Controllo extends Thread{

        public void run(){
            try{
                for(Map.Entry<Integer,Misura> coppia : databse.entrySet()){
                    long tempo = coppia.getValue().getTempo();
                    if(System.currentTimeMillis()-tempo > 10000){
                        //Inivio il messaggio di inattivita
                        byte[] buffer = new byte[256];
                        String messaggio = "Il sensore "+ coppia.getKey() +" è inattivo";
                        buffer = messaggio.getBytes();
                        InetAddress indirizzoMulticast = InetAddress.getByName(indirizzo);
                        DatagramPacket paccheto = new DatagramPacket(buffer, buffer.length, indirizzoMulticast, porta3);
                        server3.send(paccheto);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String...args){
        Server s = new Server();
    }
}
