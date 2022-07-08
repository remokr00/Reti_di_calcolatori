package Appelli.Appello_29_03_2022.EsercizioSocket;

import VoliAreoporto_WS.GestisciRichieste;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Banco {

    private HashMap<String, ArrayList<Biglietto>> lotteria; //una scommessa può avere una lista di biglietti
    private HashMap<InetAddress, ArrayList<Biglietto>> giocatori; //ai giocatori associio i bilgietti che hanno giocato
    private HashMap<String, ArrayList<InetAddress>> indirizzi; //alla scommessa associo una lista di giocatori
    private final int port1 = 3000;
    private final int mCastPort = 4000;
    private final String ip = "230.0.0.1";
    private ServerSocket server1;
    private MulticastSocket server2;

    public Banco(String id, ArrayList<Biglietto> biglietti){
        lotteria = (HashMap<String, ArrayList<Biglietto>>) Collections.synchronizedMap(new HashMap<String, ArrayList<Biglietto>>());
        giocatori = new HashMap<>(); //non c'è bisogno di farla syncronized perché accedo in lettura solo dopo che sono passsati i 60 minuti e quindi tutti i giocatori sono stati già registrati
        indirizzi = new HashMap<>(); //idem con patate
        System.out.println("Inizializzo il server...");
        new GestisciConnessioni().start();
        new GestioneLotteria(id, biglietti).start();
    }

    class GestisciConnessioni extends Thread{

        public void run(){
            try {
                server1 = new ServerSocket(port1);
                while(true){
                    Socket client = server1.accept();
                    new GestisciRichieste(client).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class GestisciRichieste extends Thread{

        private Socket client;

        public GestisciRichieste(Socket c){ client = c; }

        public void run(){
            try{

                //Ricevo dal client il nome della lotteria e il numero di biglietti
                BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String scommessa = input.readLine();
                String[] parti = scommessa.split("#");
                String id = parti[0];
                int numBiglietti = Integer.parseInt(parti[1]);
                ArrayList<Biglietto> acquistati = new ArrayList<>();
                ObjectOutputStream outputBiglietto = new ObjectOutputStream(client.getOutputStream());

                //verifico se la lotteria è ancora attiva
                synchronized (lotteria){
                    PrintWriter output = new PrintWriter(client.getOutputStream());
                    if(! lotteria.containsKey(id) ){ //non attiva
                        //Inivio messaggio di errore al client
                        output.println("Lotteria inesistente");
                        outputBiglietto.writeObject(new Biglietto(0));
                    }
                    ArrayList<Biglietto> biglietti = lotteria.get(id);
                    if(biglietti.size()<numBiglietti){ //non ci sono abbastanza bilgietti
                        //Inivio messaggio di errore al client
                        output.println("Numero biglietti non disponibile");
                        outputBiglietto.writeObject(new Biglietto(0));
                    }
                    acquistati = (ArrayList<Biglietto>)biglietti.subList(0, numBiglietti-1);
                    //rimuovo i biglietti dalla lista dato che li devo vendere
                    for( int i = 0; i<numBiglietti; i++ ){
                        biglietti.remove(i);
                    }
                }

                //invio i biglietti acquitati al client
                outputBiglietto.writeObject(acquistati);

                //aggiorno la lista dei giocatori
                giocatori.put(client.getInetAddress(), acquistati);

                //aggiorno gli indirrizzi cella scommessa
                ArrayList<InetAddress> aggiornamento = indirizzi.get(id);
                aggiornamento.add(client.getInetAddress());
                indirizzi.put(id, aggiornamento);




            }catch (Exception e){

            }
        }
    }

    class GestioneLotteria extends Thread{

        private String id;
        private ArrayList<Biglietto> biglietti;

        public GestioneLotteria (String i, ArrayList<Biglietto> b){
            id = i;
            biglietti = b;
        }

        public void run(){
            try {
                //Suppongo venga creata una lotteria
                lotteria.put(id, biglietti);
                new GestisciTempo(id).start();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class GestisciTempo extends Thread{

        private String id;

        public GestisciTempo(String i){ id = i; }

        public void run(){
            try{
                TimeUnit.MINUTES.sleep(60);
                //Una volta scaduto il tempo elimino la scommessa dal databse delle lotterie
                //calcolo casualmente un vincitore
                //comunico al vincitore
                lotteria.remove(id);
                //Calcolo il biglietto vinicitore vincitore
                int numeroVincente = (int) (Math.random()*10000+1);

                //cerco il vincitore
                InetAddress vincitore = null;
                ArrayList<InetAddress> giocatoriDiquestaScommessa = indirizzi.get(id);
                for(InetAddress giocatore : giocatoriDiquestaScommessa){
                    for(Map.Entry<InetAddress,ArrayList<Biglietto>> entry : giocatori.entrySet()){
                        if(giocatore.equals(entry.getKey())){
                            //prendo i biglietti
                            ArrayList<Biglietto> biglietti = entry.getValue();
                            for(Biglietto biglietto : biglietti){
                                if(biglietto.getNumero()==numeroVincente){
                                    vincitore = entry.getKey();
                                }
                            }
                        }
                    }
                }
                server2 = new MulticastSocket(mCastPort);
                byte[] buffer = ("Il vincitore è il client con indirizzo: "+vincitore).getBytes();
                DatagramPacket pacchetto = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip), mCastPort);
                server2.send(pacchetto);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main (String ... args){
        ArrayList<Biglietto> biglietti = new ArrayList<>();
        Banco b = new Banco("12", biglietti );
    }
}
