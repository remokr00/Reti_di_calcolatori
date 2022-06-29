package EserciziPerIlaria.Es1TCP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static void main(String...args){

        try{
            //inizalizzo gli strumenti utili
            Socket client = new Socket("localhost",8080);
            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter output = new PrintWriter(client.getOutputStream(),true);

            //creo un array di numeri, volendo si può utilizzare lo scanner
            //per fare in modo che il client li riceva da input
            String[] numeri = {"1","2","3","4"};

            //invio i numeri al server
            for(String numero : numeri){
                System.out.println("Sto inviando i numeri...");
                output.println(numero);
            }

            //ricevo la risposta e la stampo a schermo
            boolean fatto = false;
            while(!fatto){
                String risposta = input.readLine();
                if(risposta==null){
                    fatto = true;
                }
                System.out.println(risposta);
            }

            //chiudo tutti i canali
            input.close();
            output.close();
            client.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
