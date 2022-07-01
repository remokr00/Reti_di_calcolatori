package Appelli.Appello_20_06_2020.EsercizioSocket;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.PrimitiveIterator;
import java.util.Scanner;

public class Client {

    public static void main(String...args){
        try {

            //primo tipo di operazionni
            Socket client = new Socket("server.it", 3000);

            //1.1 Invio una stringa con id e grandezza
            Scanner scanner = new Scanner(System.in);
            String richiesta = scanner.nextLine();
            PrintWriter output = new PrintWriter(client.getOutputStream(), true);
            output.println(richiesta);

            //1.2 ricevo il dato relativo
            ObjectInputStream input = new ObjectInputStream(client.getInputStream());
            Dato dato = (Dato) input.readObject();
            System.out.println("Valore di "+dato.getGrandezza()+" pari a "+dato.getValore());

            //secondo tipo di operazioni
            Socket client2 = new Socket("server.it", 4000);

            //2.1 invio la richiesta
            String richiesta2 = scanner.nextLine();
            PrintWriter output2 = new PrintWriter(client2.getOutputStream(), true);
            output2.println(richiesta2);

            //2.2 ricevo il dato
            ObjectInputStream input2 = new ObjectInputStream(client2.getInputStream());
            Dato dato2 = (Dato) input2.readObject();
            System.out.println("Valore di "+dato2.getGrandezza()+" misurato  nell'intervallo di tempo richiesto pari a "+dato2.getValore());

            //chiudo la connessione
            client.close();
            client2.close();
            input.close();
            input2.close();
            output.close();
            output2.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
