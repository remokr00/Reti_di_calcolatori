package Esercitazione_2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoServerClient {

    /*
    Classe ausiliari all'utilizzo della classe EchoServer
     */
    public static void main(String...args){
        try {
            Socket s = new Socket("localhost", 8192);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            Scanner testo = new Scanner(System.in);
            String linea = testo.nextLine();
            while(!linea.equals("BYE")){
                out.println(linea); //invio la risposta al server
                //ricevo risposta dal server
                String risposta = in.readLine();
                System.out.println(risposta); //stampo a schermo la rispsota del server
                linea = testo.nextLine();
                if(testo.equals("EXIT")) {
                    if(!s.isClosed()){
                        out.println("BYE");
                        s.close();
                        in.close();
                        out.close();
                    }
                }
            }



        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
