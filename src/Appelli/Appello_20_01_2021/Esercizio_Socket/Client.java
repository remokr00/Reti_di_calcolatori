package Appelli.Appello_20_01_2021.Esercizio_Socket;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.*;

public class Client {

    public static void main(String...args){

        try {

            Socket client = new Socket("server.it", 3000);
            String id = "1";
            PrintWriter output = new PrintWriter(client.getOutputStream(),true);
            output.println(id);

            ObjectInputStream input = new ObjectInputStream(client.getInputStream());
            Misura misura = (Misura) input.readObject();
            System.out.println("La misura del sensore: "+misura.getId()+ "è di "+misura.getValore());

            MulticastSocket mClient = new MulticastSocket(5000);
            byte[] buffer = new byte[256];
            DatagramPacket paccheto = new DatagramPacket(buffer, buffer.length);
            mClient.receive(paccheto);
            System.out.println(paccheto.getData());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
