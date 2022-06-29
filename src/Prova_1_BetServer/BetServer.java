package Prova_1_BetServer;

import javax.imageio.IIOException;
import javax.naming.NamingEnumeration;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

public class BetServer {

    private HashMap<Integer, Scommessa> scommesse;
    private Calendar limite;
    private BetAccepter accepter;
    private BetDenyer denyer;
    private int port;

    public BetServer(int port, Calendar limite){

        scommesse = new HashMap<>();
        this.port = port;
        this.limite = limite;
        accepter = new BetAccepter(port);
        accepter.start();

    }

    class BetAccepter extends Thread{

        private int port;
        private ServerSocket server;
        private boolean accept;

        public BetAccepter (int port){

            try {

                this.port = port;
                server = new ServerSocket(port);
                accept = true;

            }catch (Exception e){
                e.printStackTrace();
            }

        }

        public void run(){

            try{

                while(accept){

                    Calendar ora = Calendar.getInstance(); //prendo l'attimo corrente
                    server.setSoTimeout((int)(limite.getTimeInMillis() - ora.getTimeInMillis())); //calcolo se si possono effettuare ancora scommesse
                    Socket connessioni = server.accept();

                    BufferedReader input = new BufferedReader(new InputStreamReader(connessioni.getInputStream()));
                    PrintWriter output = new PrintWriter(connessioni.getOutputStream());

                    String line = input.readLine();

                    int posizione = line.indexOf(" "); //trovo lo spazio come separatore per prendere numero cavallo e importo scommesso

                    int numeroCavallo = Integer.parseInt(line.substring(0, posizione));
                    long puntata = Long.parseLong(line.substring(posizione+1));

                    InetAddress indirizzoIP = connessioni.getInetAddress();

                    Scommessa scommessa = new Scommessa(numeroCavallo, puntata, indirizzoIP);

                    int chiave = scommessa.getID();
                    scommesse.put(chiave, scommessa);

                    //avviso che la scommessa è stata accetata

                    output.println("scommessa accettata");
                    output.close();
                    connessioni.close();

                    System.out.println("Ricevuta scommessa "+ indirizzoIP+" "+ numeroCavallo+" "+puntata);
                }

            }catch (SocketTimeoutException e){

                accept = false;
                System.out.println("Tempo per scommettere terminato");

            }catch (Exception e){
                e.printStackTrace();
            }

        } //run


    }//BetAccepter

    class BetDenyer extends Thread{

        private int port;
        private ServerSocket server;
        private boolean closed;

        public BetDenyer(int port){

            try{

                this.port = port;
                server = new ServerSocket(port);
                closed = true;

            }catch (Exception e){
                e.printStackTrace();
            }

        }

        public void reset(){ closed = false; }

        public void run(){

            try{

                while( closed ){

                    Socket connessioni = server.accept();

                    PrintWriter output = new PrintWriter(connessioni.getOutputStream(), true);

                    output.println("Scommessa rifiutata");
                    output.close();

                    connessioni.close();

                    System.out.println("Scommessa rifiutata");

                }

                server.close();

            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }//BetDenyer

    public void accettaScommessa(){

        try{

            accepter.join();

        }catch (Exception e){
            e.printStackTrace();
        }

    }//accettaScommessa

    public void rifiutaScommessa(){

        denyer = new BetDenyer(port);
        denyer.start();

    }//rifiutaScommesse

    public void resetServer(){ denyer.reset(); }

    public LinkedList<Scommessa> controllaScommesse (int cavalloVincente){

        LinkedList<Scommessa> elenco = new LinkedList<>();

        for(int i=0; i<scommesse.size(); i++){

            Scommessa scommessa = scommesse.get(i);
            if(scommessa.equals(new Scommessa(cavalloVincente, 0, null))) elenco.addLast(scommessa);

        }

        return elenco;
    }

    public void comunicaVincitori(LinkedList<Scommessa> vincitori, InetAddress ind, int port){

        ListIterator<Scommessa> iteratore = vincitori.listIterator();

        try{

            MulticastSocket server = new MulticastSocket();
            byte[] buffer = new byte[256];

            String messaggio = "";

            int quota = 12;

            while(iteratore.hasNext()){

                Scommessa scommessa = iteratore.next();

                messaggio += scommessa.getScommettitore()+" "+(scommessa.getPuntata()*quota)+"\n";

            }

            buffer = messaggio.getBytes();

            DatagramPacket pacchetto = new DatagramPacket(buffer, buffer.length, ind, port);
            server.send(pacchetto);


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main (String[] args){
        int serverPort=8001; // su cui il BetServer riceve, tramite socket tcp, le scommesse
        int clientPort=8002; // su cui il BetServer invia ai client, in multicast, i risultati
        try{
            Calendar deadline=Calendar.getInstance();
            deadline.add(Calendar.MINUTE,1); //la deadline è fissata tra un minuto
            BetServer server=new BetServer(serverPort, deadline);
            System.out.println("Scommesse aperte");
            server.accettaScommessa(); // accetta fino alla scadenza della deadline
            server.rifiutaScommessa(); // dopo la deadline ogni scommessa viene rifiutata
            int vincente=((int)(Math.random()*12))+1;
            System.out.println("E' risultato vincente il cavallo: "+vincente);
            LinkedList<Scommessa> elencoVincitori=server.controllaScommesse(vincente);
            InetAddress multiAddress=InetAddress.getByName("230.0.0.1");
            server.comunicaVincitori(elencoVincitori, multiAddress, clientPort);
            Thread.sleep(120000);
            server.resetServer();
        } catch (InterruptedException ie) {
            System.out.println(ie);
        } catch (UnknownHostException uhe) {
            System.out.println(uhe);
        }
    }// main

}
