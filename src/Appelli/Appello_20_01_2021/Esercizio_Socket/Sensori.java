package Appelli.Appello_20_01_2021.Esercizio_Socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Sensori {

    private int id;
    private Misura misura;

    public Sensori(int i){
        id = i;
        misura = new Misura(i);
        new InviaDati().start();
    }

    class InviaDati extends Thread{

        public void run(){
            try{
                DatagramSocket sensore = new DatagramSocket();
                String messaggio = ""+misura.getId()+"#"+misura.getValore();
                byte[] buffer = (messaggio).getBytes();
                InetAddress indirizzoUDP = InetAddress.getByName("server.it");
                DatagramPacket paccheto = new DatagramPacket(buffer, buffer.length, indirizzoUDP, 4000);
                sensore.send(paccheto);
                Thread.sleep(5000);
                Sensori sensori = new Sensori(id);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    
}
