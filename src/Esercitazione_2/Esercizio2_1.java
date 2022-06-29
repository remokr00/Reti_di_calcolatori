package Esercitazione_2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Esercizio2_1 {

    public static void main(String...args){
        try {
            int port = 80;
            Socket client = new Socket("HttpWelcome", port);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
            out.println("GET /index.php HTTP/1.1");
            out.println("Host: HttpWelcome");
            out.println();
            boolean more = true;
            while (more) {
                String line = in.readLine();
                if (line == null) more = false;
                else System.out.println(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
