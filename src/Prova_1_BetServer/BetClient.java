package Prova_1_BetServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

public class BetClient {

    //queste sono le variabili di istanza utili

    private int portaDelServer, portaLocale;
    private InetAddress indirizzoDelGruppo, indirizzoDelServer;
    private Socket client;

    //iizializzo il costruttore

    public BetClient(InetAddress indirizzoDelGruppo, InetAddress indirizzoDelServer, int portaDelServer, int portaLocale){

        this.indirizzoDelGruppo=indirizzoDelGruppo;
        this.indirizzoDelServer=indirizzoDelServer;
        this.portaDelServer=portaDelServer;
        this.portaLocale=portaLocale;

        try{

            client=new Socket(indirizzoDelServer, portaDelServer);

        }catch (Exception e){
            e.printStackTrace();
        }

    }//BetClient

    public boolean piazzaScommessa(int numeroCavallo, long puntata){

        String accettazioneScommessa="";

        try{

            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter output = new PrintWriter(client.getOutputStream());

            String scommessa = numeroCavallo + " " + puntata;
            output.println(scommessa); //invio la scommessa al server
            accettazioneScommessa = input.readLine();

        }catch (Exception e){
            e.printStackTrace();
        }

        return accettazioneScommessa.equals("scommessa accettata"); //se accettata TRUE se rifiutata FALSE

    }//piazzaScommessa

    public void riceviElencoVincitori(){

        try{

            MulticastSocket client = new MulticastSocket(portaLocale);
            client.joinGroup(indirizzoDelGruppo);

            byte buffer[] = new byte[256];
            DatagramPacket pacchetto = new DatagramPacket(buffer, buffer.length);
            client.receive(pacchetto);
            String elencoVincitori = new String(pacchetto.getData());

            System.out.println("Elenco vincitori: ");
            System.out.println(elencoVincitori);

        }catch (Exception e){
            e.printStackTrace();
        }

    }//ricevi elenco vincitori

    //creo ora il main
    public static void main(String... args){

        int portaDelServer = 8001;
        int portaLocale = 8002;

        try{

            InetAddress indirizzoDelGruppo = InetAddress.getByName("230.0.0.1");
            InetAddress indirizzoDelServer = InetAddress.getByName("127.0.0.1");

            BetClient client = new BetClient(indirizzoDelGruppo, indirizzoDelServer, portaDelServer, portaLocale);

            int cavallo = ((int)(Math.random()*12))+1; //NUMERO RANDOM TRA 1 E 12
            int puntata = ((int)(Math.random()*100))+1; //NUMERO RANDOM TRA 1 E 100

            if(client.piazzaScommessa(cavallo, puntata))
                client.riceviElencoVincitori();

        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
