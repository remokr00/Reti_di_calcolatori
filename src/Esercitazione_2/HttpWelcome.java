package Esercitazione_2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class HttpWelcome {

    private static int port=80;
    private static String HtmlWelcomeMessage(){
        return "<html>\n"+
                "<head>\n"+
                "<title>UNICAL-Ingegneria Informatica</title>\n"+
                "<head>\n"+
                "<body>\n"+
                "<h2> align=\"center\">\n"+
                "<font color=\"#0000FF\"> Benvenuti al corso di Reti di calcolatori</font>\n"+
                "</h2>\n"+
                "</body>\n"+
                "</html>";
    }

    public static void main(String...args){
        try {
            ServerSocket server = new ServerSocket(port);
            while(true){
                Socket client =server.accept(); //aspetto tutte le possibili conessioni dei vari client
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out= new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
                String request = in.readLine();
                System.out.println("Request: "+request);
                StringTokenizer st = new StringTokenizer(request);
                if((st.countTokens()>=2) && st.nextToken().equals("GET")){
                    String messagge = HtmlWelcomeMessage();
                    out.println("HTTP/1.0 200 0K");
                    out.println("Content-Length: "+messagge.length());
                    out.println("Content-Type: text/html");
                    out.println();
                }else{
                    out.println("400 Bad Request");
                }
                out.flush();
                client.close();


            }
        }catch (Exception e){ e.printStackTrace();}


    }

}
