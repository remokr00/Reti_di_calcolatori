package Esercitazione_1;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Lookup_di_indirizzi {

    /*
    Stampa tutte le informazioni di rete associate alla macchina locale
     */
    static void printLocalAddress(){
        try{
            InetAddress mioIP= InetAddress.getLocalHost();
            System.out.println("Il mio nome è: "+ mioIP.getHostName());
            System.out.println("Il mio IP è: "+ mioIP.getHostAddress());
        }catch (UnknownHostException e){
            e.printStackTrace();
        }
    }

    /*
   Stampa tutte le informazioni di rete associate ad un host
    al quale accediamo tramite internet
    */
    static void printRemoteAddress(String nome){
        try{
            InetAddress remoteHost= InetAddress.getByName(nome);
            System.out.println("Il nome dell'host è: "+ remoteHost.getHostName());
            System.out.println("L'IP dell'host è: "+ remoteHost.getHostAddress());
        }catch (UnknownHostException e){
            e.printStackTrace();
        }
    }
/*
 restituisce tutti i possibili indirizzi IP associati a quella macchina
 */
    static void printAllRemoteAddress(String nome) {
        try{
            InetAddress[] remoteHost= InetAddress.getAllByName(nome);
            for(InetAddress i: remoteHost){
                System.out.println("Il nome dell'host è: "+ i.getHostName());
                System.out.println("L'IP dell'host è: "+ i.getHostAddress());
            }
        }catch (UnknownHostException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args){

        //richiamo i metodi
        printLocalAddress();
        System.out.println("-----------------");
        printRemoteAddress("www.dimes.unical.it");
        System.out.println("-----------------");
        printRemoteAddress("8.8.4.4"); //indirizzo ip del DNS secondario utilizzato da google
        System.out.println("-----------------");
        printAllRemoteAddress("dns.google");
    }
}
