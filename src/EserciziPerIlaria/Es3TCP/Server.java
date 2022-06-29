package EserciziPerIlaria.Es3TCP;

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

            //leggo la frase
            String frase = input.readLine();

            //eseguo le operazioni
            String[] parole = frase.split("#");
            int numeroParole = parole.length;
            int caratteri = 0;
            for(String parola : parole){
                caratteri+=parola.length();
            }

            //Invio il risultato al client
            output.println("La frase è composta da "+ numeroParole +" parola;");
            output.println("La frase è composta da "+ caratteri +" caratteri");

            //chiudo tutti i canali
            client.close();
            input.close();
            output.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
