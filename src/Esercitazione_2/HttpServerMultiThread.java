package Esercitazione_2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class HttpServerMultiThread {

    private static final int port = 3575;

    public static void main(String...args){
        try{
            ServerSocket server = new ServerSocket(port);
            System.out.println("HTTP server running on port: "+port);
            while(true){
                Socket client = server.accept();
                ThreadedServer cc = new ThreadedServer(client);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

class ThreadedServer extends Thread{

    private Socket client;
    private BufferedReader is;
    private DataOutputStream os;

    public ThreadedServer (Socket s){
        client=s;
        try{
            is = new BufferedReader(new InputStreamReader(client.getInputStream()));
            os = new DataOutputStream(client.getOutputStream());
        }catch (Exception e ){
            e.printStackTrace();
            try {
                client.close();
            }catch (IOException ex){
                ex.printStackTrace();
            }
            return;
        }
        this.start();
    }

    public void run(){
        try{
           String request = is.readLine();
           System.out.println("Request: "+request);
            StringTokenizer st = new StringTokenizer(request);
            //serie di controlli sofisticati dell'HTTP server
            if((st.countTokens()>=2) && st.nextToken().equals("GET")){
                if((st.nextToken().startsWith("/")))
                    request = request.substring(1);
                if((request.endsWith("/")) || request.equals(""))
                    request=request+"index.html";
                if((request.indexOf("..")!=1) || request.startsWith("/")){
                    os.writeBytes("403 Forbidden. "+"You do not have enough privileges to read: "+ request+"\r\n");
                }else{
                    //File f = new File(request); reply (os, f);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void reply(DataOutputStream out, File f) throws Exception{
        try{
            DataInputStream in = new DataInputStream(new FileInputStream(f));
            int len = (int) f.length();
            byte buf[] = new byte[len];
            in.readFully(buf);
            out.writeBytes("HTTP/1.0 200 OK \r\n");
            out.writeBytes("Content-Length: "+buf.length+"\r\n");
            out.writeBytes("Content-Type: text/html\r\n\r\n");
            out.write(buf);
            out.flush();
            in.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}