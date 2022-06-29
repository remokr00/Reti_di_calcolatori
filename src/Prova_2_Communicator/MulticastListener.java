package Prova_2_Communicator;

import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

public class MulticastListener extends Thread{

    int portaMulticast; //usata per i messaggi multicast
    int portaTCP; //è la porta su cui il communicator è in ascolto per ricevere messaggi TCP
    InetAddress indirizzoRemoto;

    public MulticastListener(int portaMulticast, int portaTCP){
        this.portaMulticast = portaMulticast;
        this.portaTCP = portaTCP;
        System.out.println("ML> Porta Multicast locale = " + portaMulticast);
        System.out.println("ML> Porta TCP locale = " + portaTCP);
    }//costruttore

    public void run(){

        try{
            //si iscrive al gruppo su cui viene inviato il msg in muticast
            MulticastSocket multicastSocket = new MulticastSocket(portaMulticast);
            InetAddress indirizzoGruppo = InetAddress.getByName("230.0.0.1");
            multicastSocket.joinGroup(indirizzoGruppo);
            while(true){
                //Riceve datagramma multicast ed estrae da esso l'indirizzo del mittente
                // (remAdress) è la porta del modulo server del mittente (remTCPPort)
                //cioè la porta su cui il mittente è in ascolto per ricevere il messaggio TCP
                byte buffer[] = new byte[256];
                DatagramPacket pacchetto = new DatagramPacket(buffer, buffer.length);
                multicastSocket.receive(pacchetto);
                indirizzoRemoto = pacchetto.getAddress();
                String messaggioRicevuto = new String(pacchetto.getData());
                int i = 0;
                while (Character.isDigit(messaggioRicevuto.charAt(i)))
                    i++;
                int portaRemotaTCP = Integer.parseInt(messaggioRicevuto.substring(0, i));
                System.out.println("\nML>Ricevuto multicast da "+ indirizzoRemoto.getHostAddress() +": "+ pacchetto.getPort());
                System.out.println("ML>Contenuto del datagram: "+portaRemotaTCP);
                //Invio messaggio Via TCP solo se il datagramma multicast
                //era stato iiato da un altro COmmunicatore e non da se stesso
                //il messaggio di risposta conteine la porta TCP del Communicator che risponde
                //cioè, la porta su cui esso riceve messagi TCP
                if(!(indirizzoRemoto.equals(InetAddress.getLocalHost())) || (portaRemotaTCP != portaTCP)){
                    System.out.println("ML> invio rispsota a "+ indirizzoRemoto.getHostAddress() +":"+ portaRemotaTCP);
                    Socket TCPsocket = new Socket(indirizzoRemoto.getHostAddress(), portaRemotaTCP);
                    PrintWriter output = new PrintWriter(TCPsocket.getOutputStream(), true);
                    output.println(portaTCP);
                }//if
            }//while
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
