package Appelli.Appello_15_01_2020.EsercizioSocket;


import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

public class CentroBenessere {

    private final int serverPort = 3333;
    private final String hostName = "gestore.dimes.unical.it";
    private Socket centro;
    private MulticastSocket centroMulticast;
    private final int mPort = 2222;
    private final String nome = "Centro Benessere X";
    private InetAddress indirizzoMulticast;
    private PrintWriter output;


    public CentroBenessere(){
        try{
            centro = new Socket(hostName, serverPort);
            centroMulticast = new MulticastSocket(mPort);
            indirizzoMulticast = InetAddress.getByName(hostName);
            output = new PrintWriter(centro.getOutputStream(), true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

   private boolean invioOfferta(){
        double numero = Math.random();
        if(numero > 0.3) return true;
        return false;
   }

   private String generaOfferta(){
        int prezzo = (int)Math.random()*150+50;
        return nome + ","+prezzo;
   }

   public void gestisciRichiesta(){
        try {
            //mi unisco al gruppo e ricevo il pacchett
            centroMulticast.joinGroup(indirizzoMulticast);
            byte[] buffer = new byte[256];
            DatagramPacket pacchetto = new DatagramPacket(buffer, buffer.length);
            centroMulticast.receive(pacchetto);
            System.out.println("Ho ricevuto la seguente richiesta: ");
            System.out.println(pacchetto.getData());
            centroMulticast.leaveGroup(indirizzoMulticast);
            centroMulticast.close();
        }catch (Exception e){
            e.printStackTrace();
        }
   }

   public void gestisciRisposta(){
        try{
            //Invio l'offerta
            String offerta = generaOfferta();
            System.out.println("Invio l'offerta...");
            output.println(offerta);
            output.close();
            centro.close();
        }catch (Exception e){
            e.printStackTrace();
        }
   }

   public static void main(String...args){
        CentroBenessere cb = new CentroBenessere();
        cb.gestisciRichiesta();
        if(cb.invioOfferta())
            cb.gestisciRisposta();
   }

}
