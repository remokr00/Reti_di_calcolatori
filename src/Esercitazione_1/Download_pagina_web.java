package Esercitazione_1;

import java.io.*;
import java.net.Socket;


public class Download_pagina_web {

    /*
    Questa applicazione tramite l'utilizzo dei socket ci consentira di scaricare una
    pagina web
     */

    public static void main(String[] args){

        try{
            String URL= "stackoverflow.com"; //pagina web che vogliamo scaricare
            Socket s = new Socket(URL, 80);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream())); //creo il buffer per leggere la risposta dal server
            //Invio la richiesta al server rispettando il formato HTTP sfruttando il PrintWriter
            out.println("GET /question HTTP/1.1");
            out.println("Host: "+URL);
            out.println();
            //creo un ciclo per leggere tutte le righe della rispsota
            boolean more = true;
            while(more){
                String line = in.readLine();
                if(line==null) more=false;
                else System.out.println(line);
            }

        }catch (IOException e){e.printStackTrace();}
    }
}
