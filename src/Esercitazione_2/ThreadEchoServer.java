package Esercitazione_2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadEchoServer {

    /*
    Versione dell'echo server in grado di gestire connessioni multiple
     */

    public static void main(String...args){
        try{
            ServerSocket s = new ServerSocket(8192);
            //continuo ad accettare connessioni fino a quando se ne creano
            while(true){
                System.out.println(s.toString());
                Socket incoming = s.accept();
                ThreadedEchoHandler t = new ThreadedEchoHandler(incoming);
                t.start();
            }
        }catch (Exception e){ e.printStackTrace();}
    }

    static class ThreadedEchoHandler extends Thread{

        private Socket incoming;

        public ThreadedEchoHandler(Socket s){
            incoming=s;
        }

        public void run(){
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
                PrintWriter out=new PrintWriter(incoming.getOutputStream());
                out.println("Hello! Enter Bye to exit");
                boolean done = false;
                while(!done){
                    String line= in.readLine();
                    if(line==null) done=true;
                    else{
                        out.println("Echo: "+line);
                        if (line.trim().equals("BYE")) done=true;
                    }
                }
                incoming.close();
            }catch (Exception e){ e.printStackTrace();}
        }
    }
}
