package EserciziPerIlaria.Es1TCP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String...args){

        try{
            //creo il server e accetto le connessioni
            ServerSocket server = new ServerSocket(8080);
            Socket client = server.accept();

            //Creo glii stream per la comunicazione
            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter output = new PrintWriter(client.getOutputStream(), true);

            //eseguo le operazioni
            System.out.println("Sto ricevendo i numeri...");
            boolean fatto = false;
            while(!fatto){
                String numero = input.readLine();
                if(numero == null){
                    fatto=true;
                }
                Integer num = Integer.parseInt(numero);
                if(num%2 == 0){
                    output.println("Il numero "+ num +" è pari");
                }
                else{
                    output.println("Il numero "+ num +" è dispari");
                }
            }

            //chiudo tutti i canali
            client.close();
            input.close();
            output.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
