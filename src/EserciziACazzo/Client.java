package EserciziACazzo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String []args){
        try{
            String[] numeri = {"2", "3", "5", "6"};
            Socket s = new Socket("localhost", 1234);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            for(String st: numeri){
                out.println(st);
            }
            boolean done = false;
            while(!done){
                String ris = in.readLine();
                if(ris==null) done = true;
                System.out.println(ris);
            }
            in.close();
            out.close();
            s.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
