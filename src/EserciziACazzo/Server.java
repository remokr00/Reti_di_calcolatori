package EserciziACazzo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String []args){
        try{
            ServerSocket s = new ServerSocket(1234);//creo una connessione
            Socket client = s.accept();//mi metto in ascolto di eventuali client
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream());
            boolean done = false;
            while(!done){
                String num = in.readLine();
                int n = Integer.parseInt(num);
                if(n==-1) done = true;
                int doppio = 2*n;
                String msg = ""+doppio;
                out.println(msg);
            }
            in.close();
            out.close();
            client.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
