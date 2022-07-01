package Appelli.Appello_20_06_2020.EsercizioSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Centralina {

    private List<Double> grandezze; //suppongo pos 0 = temperatura pos 1 = umidità pos 2 = pressione
    private int id;
    private Socket centralina1;
    private DatagramSocket centralina2;
    private final int serverPort1 = 5000;
    private final int serverPort2 = 6000;
    private final String host = "server.it";

    public Centralina(int id){
        this.id = id;
        grandezze = new ArrayList<>();
        System.out.println("Avvio la centralina "+ id);
        avvia();
    }

    private void avvia(){
        try{
            centralina1 = new Socket(host, serverPort1);
            new ThreadCentralina1().start();
            centralina2 = new DatagramSocket(serverPort2);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class ThreadCentralina1 extends Thread{

        public void run(){
            try {
                //1.1 invio il mio id
                PrintWriter output = new PrintWriter(centralina1.getOutputStream(),true);
                output.println(id);

                //1.2 ricevo il tempo per inviare le misure
                BufferedReader input = new BufferedReader(new InputStreamReader(centralina1.getInputStream()));
                long time =  Long.parseLong(input.readLine());

                while (true) {
                    new Calcola().start();
                    Calcola.sleep(time);
                }
            }catch (Exception e){
              e.printStackTrace();
            }
        }

        class Calcola extends Thread{

            public void run(){
                try{
                    for(int i=0; i<grandezze.size(); i++){
                         grandezze.set(i, (Math.random()*30)+1 ) ;
                        //Invio al server i dati sulla porta UDP
                        byte buffer[];
                        buffer = (""+grandezze.get(i)).getBytes();
                        InetAddress indirizzoServer = InetAddress.getByName(host);
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, indirizzoServer , serverPort2);
                        centralina2.send(packet);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String ... args){
        try{
            int id = 0;
            while(id <10){
                Centralina c = new Centralina(id);
                id++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
