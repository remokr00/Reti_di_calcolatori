package Appelli.Appello_11_07_2019.Esercizio_Socket;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Porto {

    private final int port1 = 3000, port2 = 4000, port3 = 5000;
    private final String ipOp = "operatore.it";
    private ServerSocket server1;
    private Socket server2;
    private DatagramSocket server3;
    private ArrayList<Socket> meno40;
    private ArrayList<Socket> piu40;
    private LinkedList<Long> tempo;
    private LinkedList<Socket> naviInAttesaMeno40;
    private LinkedList<Socket> naviInAttesaPiu40;
    private int tipo; //0 meno40, 1 piu40

    public Porto(){
        meno40 = (ArrayList<Socket>) Collections.synchronizedList(new ArrayList<Socket>());
        piu40 = (ArrayList<Socket>) Collections.synchronizedList(new ArrayList<Socket>());
        tempo = (LinkedList<Long>) Collections.synchronizedList(new LinkedList<Long>());
        naviInAttesaMeno40 = (LinkedList<Socket>) Collections.synchronizedList(new ArrayList<Socket>());
        naviInAttesaPiu40 = (LinkedList<Socket>) Collections.synchronizedList(new ArrayList<Socket>());
        System.out.println("Inizializzo il server");
        new GestisciConnessioni().start();
        new Messaggio().start();
    }

    class GestisciConnessioni extends Thread{

        public void run(){
            try{
                server1 = new ServerSocket(port1);
                while(true){
                    Socket nave = server1.accept();
                    new GestisciNavi(nave).start();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class GestisciNavi extends Thread{

        private Socket nave;

        public GestisciNavi(Socket n){ nave = n; }

        public void run(){
            try{
                //ricevo richiesta dalla nave
                BufferedReader input = new BufferedReader(new InputStreamReader(nave.getInputStream()));
                String richiesta = input.readLine();
                input.close();
                String[] parti = richiesta.split("#");
                Double lunghezza = Double.parseDouble(parti[1]);

                //verifico la disponibilità della banchina
                PrintWriter output = new PrintWriter(nave.getOutputStream(), true);
                server2 = new Socket(ipOp, port2);
                PrintWriter output2 = new PrintWriter(server2.getOutputStream(), true);
                if(lunghezza<40){
                    tipo = 0;
                    if(meno40.size()<5){
                        //invio alla nave la posizione
                        output.println("Posto Banchina: "+meno40.size()+1); //numero banchina alla quale è assegnata
                        output.close();
                        nave.close();
                        synchronized (meno40) {
                            meno40.add(nave);
                        }
                        //comunicao all'operatore di svuortare
                        output.println("Svoutare la nave in posizione: "+meno40.size()+1);
                        new SvuotaNave(server2, nave, tipo).start();

                    }
                    else{
                        synchronized (naviInAttesaMeno40){
                            naviInAttesaMeno40.add(nave);
                        }
                    }
                }
                if(lunghezza>40){
                    tipo = 1;
                    if(piu40.size()<5){
                        //invio alla nave la posizione
                        output.println("Posto banchina: "+ piu40.size()+5); //numero banchina alla quale è assegnata
                        output.close();
                        nave.close();
                        synchronized (piu40) {
                            piu40.add(nave);
                        }
                        //comunicao all'operatore di svuortare
                        output.println("Svoutare la nave in posizione: "+meno40.size()+1);
                        new SvuotaNave(server2, nave, tipo).start();
                    }
                    else{
                        //aggiungo la nave in lista di attesa
                        synchronized (naviInAttesaPiu40){
                            naviInAttesaPiu40.add(nave);
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        class SvuotaNave extends Thread{

            private Socket server2;
            private Socket nave;
            private int tipo;

            public SvuotaNave(Socket s, Socket n, int t){
                server2 = s;
                nave = n;
                tipo = t;
            }

            public void run(){
                try {
                    //svuoto
                    long t = (long)( Math.random() * 5 )+ 2;
                    synchronized (tempo){
                        tempo.add(t);
                    }
                    //invio anche all'operatore il tempo così coordino i thread
                    PrintWriter output = new PrintWriter(server2.getOutputStream(),true);
                    output.println(tempo);
                    output.close();
                    TimeUnit.MINUTES.sleep(t);
                    //ricevo la stringa d ok dall'operatore
                    BufferedReader input = new BufferedReader(new InputStreamReader(server2.getInputStream()));
                    String ok = input.readLine();
                    System.out.println(ok);
                    if(tipo == 0){
                        synchronized (meno40) {
                            meno40.remove(nave);
                        }
                        //verifico se esistono navi in attesa
                        if(naviInAttesaMeno40.size()>0){
                            synchronized (naviInAttesaMeno40){
                                synchronized (meno40){
                                    while(meno40.size()<5){
                                        meno40.add(naviInAttesaMeno40.getFirst());
                                        output.println("Posto Banchina: "+meno40.size()+1); //numero banchina alla quale è assegnata
                                        output.close();
                                        new SvuotaNave(server2, nave, tipo).start();
                                        nave.close();
                                    }
                                    naviInAttesaMeno40.removeFirst();
                                }
                            }
                        }
                    }
                    else{
                        synchronized (piu40) {
                            piu40.remove(nave);
                        }
                        //verifico se esistono navi in attesa
                        if(naviInAttesaMeno40.size()>0){
                            synchronized (naviInAttesaPiu40){
                                synchronized (piu40){
                                    while(piu40.size()<5){
                                        piu40.add(naviInAttesaPiu40.getFirst());
                                        output.println("Posto Banchina: "+piu40.size()+1); //numero banchina alla quale è assegnata
                                        output.close();
                                        new SvuotaNave(server2, nave, tipo).start();
                                        nave.close();
                                    }
                                    naviInAttesaPiu40.removeFirst();
                                }
                            }
                        }
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    class Messaggio extends Thread{

        public void run(){
            try{
                server3 = new DatagramSocket(port3);
                //calcolo la media
                double media = 0;
                double somma = 0;
                synchronized (tempo){
                    for(Long t : tempo){
                        somma += (double) t;
                    }
                }
                media = somma/tempo.size();
                while(true) {
                    for(Socket nave : naviInAttesaMeno40){
                        byte[] buffer = (""+media).getBytes();
                        DatagramPacket pacchetto = new DatagramPacket(buffer, buffer.length, nave.getInetAddress(), port3);
                        server3.send(pacchetto);
                    }
                    for(Socket nave : naviInAttesaMeno40){
                        byte[] buffer = (""+media).getBytes();
                        DatagramPacket pacchetto = new DatagramPacket(buffer, buffer.length, nave.getInetAddress(), port3);
                        server3.send(pacchetto);
                    }
                    TimeUnit.MINUTES.sleep(5);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(){
        Porto p = new Porto();
    }
}
