package IndexServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static void main(String...args){

        //invia file allo storage server
        try{
            File file = new File("Gatto", new String[] {"aaa", "bbb"}, "");
            Socket client = new Socket("localhost",2000);
            ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
            output.writeObject(file);
            output.flush();
            client.close();
            output.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        //ricerca file
        try{
            String ricerca = "Gatto#aaa,bbb";
            Socket client = new Socket("localhost", 4000);
            PrintWriter output = new PrintWriter(client.getOutputStream(), true);
            output.println(ricerca);
            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String risultato = input.readLine();
            System.out.println("RIsultato = "+risultato);
            input.close();
            output.close();
            client.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
