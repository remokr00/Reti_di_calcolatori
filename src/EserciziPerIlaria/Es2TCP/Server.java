package EserciziPerIlaria.Es2TCP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    public static void main(String...args){

        try{
            //creo il server e accetto le connessioni
            ServerSocket server = new ServerSocket(8080);
            Socket client = server.accept();

            //Creo glii stream per la comunicazione
            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter output = new PrintWriter(client.getOutputStream(), true);

            //Devo memorizzare informazioni quindi creo una struttura dati apposita
            ArrayList<Integer> addendi = new ArrayList<>();

            //eseguo le operazioni
            System.out.println("Sto ricevendo i numeri...");
            boolean fatto = false;
            while(!fatto){
                String numero = input.readLine();
                if(numero == null){
                    fatto=true;
                }
                Integer num = Integer.parseInt(numero);
                //Aggiungo il numero alla lista
                addendi.add(num);
            }

            //Eseguo la somma
            int somma = 0;
            for(Integer numero: addendi) {
                somma+=numero;
            }

            //Restituisco la somma al client
            String risultato = ""+somma;
            output.println(risultato);

            //chiudo tutti i canali
            client.close();
            input.close();
            output.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
