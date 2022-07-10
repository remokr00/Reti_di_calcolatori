package Appelli.Appello_10_02_2021.Esercizio_Socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;

public class Server {

    private final int port1 = 3000;
    private ServerSocket server1;
    private final int mastPort = 6000;
    private final String ipMCast = "230.0.0.1";
    private MulticastSocket server2;
    private final int port2 = 4000;
    private DatagramSocket server3;
    private HashMap<InetAddress, ArrayList<Ordine>> ordini;
    private HashMap<String, Integer> prodotto;
    private ArrayList<Integer> clienti;

    public Server(){
        System.out.println("Inizializzo il server");
        ordini = new HashMap<>();
        prodotto = new HashMap<>();
        clienti = new ArrayList<>();
        Collections.synchronizedMap(ordini);
        Collections.synchronizedMap(prodotto);
        new GestisciConnessioni().start();
    }

    class GestisciConnessioni extends Thread{

        public void run(){
            try{
                server1 = new ServerSocket(port1);
                while(true){
                    Socket agente = server1.accept();
                    new GestisciAgente1(agente).start();
                    new GestisciAgente2(agente).start();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class GestisciAgente1 extends Thread{

        private Socket agente;

        public GestisciAgente1(Socket a){
            agente = a;
        }

        public void run(){
            try{
                //ricevo un ordine dall'agente
                ObjectInputStream input = new ObjectInputStream(agente.getInputStream());
                Ordine ordine = (Ordine) input.readObject();

                //Salvo la richiesta dell'agente
                synchronized (ordini) {
                    if (!ordini.containsKey(agente.getInetAddress())) {
                        ArrayList<Ordine> listaOrdini = new ArrayList<>();
                        listaOrdini.add(ordine);
                        ordini.put(agente.getInetAddress(), listaOrdini);
                    } else {
                        //aggiorno la lista
                        ArrayList<Ordine> lista = ordini.get(agente.getInetAddress());
                        lista.add(ordine);
                        ordini.put(agente.getInetAddress(), lista);
                    }
                }
                //effettuo i vari controlli
                PrintWriter output = new PrintWriter(agente.getOutputStream(), true);
                if(!clienti.contains(ordine.getIdacquirente())){
                    output.println("0: Cliente non trovato");
                }
                synchronized (prodotto){
                    if(!prodotto.containsKey(ordine.getProdotto()) || prodotto.get(ordine.getProdotto())==0){
                        output.println("-1: prodotto non disponibile");
                    }
                }

                if(clienti.contains(ordine.getIdacquirente()) && prodotto.containsKey(ordine.getProdotto()) && prodotto.get(ordine.getProdotto())>0){
                    output.println("1: OK");

                    //diminuisco la quantità del prodotto richiesto
                    synchronized (prodotto) {
                        prodotto.put(ordine.getProdotto(), prodotto.get(ordine.getProdotto()) - ordine.getQuantita());
                        if (prodotto.get(ordine.getProdotto()) == 0) {
                            //avviso che sono finite le quanità per quel prodotto
                            server2 = new MulticastSocket(mastPort);
                            byte[] buffer = ("QUnatità del prodotto: " + ordine.getProdotto() + " terminate").getBytes();
                            InetAddress indirizzo = InetAddress.getByName(ipMCast);
                            DatagramPacket pacchetto = new DatagramPacket(buffer, buffer.length, indirizzo, mastPort);
                            server2.send(pacchetto);
                        }
                    }
                }
                input.close();
                output.close();
                agente.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class GestisciAgente2 extends Thread{

        private Socket agente;

        public GestisciAgente2(Socket a){
            agente = a;
        }

        public void run(){
            try{
                //ricevo dagli agenti la data
                server3 = new DatagramSocket(port2);
                byte[] buffer = new byte[256];
                DatagramPacket pacchetto = new DatagramPacket(buffer, buffer.length);
                server3.receive(pacchetto);
                String date = new String(pacchetto.getData());
                String[] parti = date.split("#");
                Integer inizio = Integer.parseInt(parti[0]);
                Integer fine = Integer.parseInt(parti[1]);
                int cont = 0;
                synchronized (ordini) {
                    for (Map.Entry<InetAddress, ArrayList<Ordine>> entry: ordini.entrySet()){
                        if(entry.getKey().equals(agente.getInetAddress())){
                            ArrayList<Ordine> ordini = entry.getValue();
                            for(Ordine ordine : ordini){
                                if(ordine.getData()<= fine && ordine.getData()>= inizio){
                                    cont++;
                                }
                            }
                        }
                    }
                }

                //invio la risposta
                buffer = (""+cont).getBytes();
                DatagramPacket pacchetto2 = new DatagramPacket(buffer, buffer.length, agente.getInetAddress(), port2);
                server3.send(pacchetto2);

                agente.close();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String ... args){
        Server s = new Server();
    }
}
