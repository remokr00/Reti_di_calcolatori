package IndexServer;

import java.io.ObjectInputStream;
import java.net.*;
import java.util.HashMap;

public class StorageServer {

    //devo memorizzare file utilizzo una struttura dati apposita
    private HashMap<String, File> data;

    private final int portaStorageServer = 2000; //porta sulla quale attendo le connessioni del client

    private final int portaIndexServer = 3000;
    private final String indirizzoIndexServer;

    private ServerSocket server;

    public StorageServer(String indirizzoIndexServer){
        this.indirizzoIndexServer = indirizzoIndexServer;
        data = new HashMap<String, File>();
        System.out.println("Storage server in fase di avvio ... ");
        inizia();
    }

    public void inizia(){
        try{
            server = new ServerSocket(portaStorageServer);
        }catch (Exception e){
            e.printStackTrace();
        }
        while (true) memorizzaFile();
    }

    private void memorizzaFile(){

        try{

            //1. RICEVI FILE
            System.out.println("in attesa del file ...");
            Socket client = server.accept();
            ObjectInputStream input = new ObjectInputStream(client.getInputStream());
            File file = (File) input.readObject();

            //1.1 Memorizzo il file
            data.put(file.getFileName(), file);
            System.out.println("Ho memorizzato il file: "+ file.getFileName());

            //2 Invio l'informazione all'indexServer
            System.out.println("Invio datagramma all'indexServer ...");
            DatagramSocket serverUDP = new DatagramSocket();

            //2.1 preparo la stinga da inviare File#attrbuto1,attributo2...
            String fileDaInviare = file.getFileName()+"#";
            for(int i=0; i<file.getKeyWords().length; i++) fileDaInviare+=file.getKeyWords()[i]+",";

            //2.2 preparo il pacchetto e lo invio
            byte buffer[] = new byte[256];
            InetAddress inetAddressIndex = InetAddress.getByName(indirizzoIndexServer);
            DatagramPacket pacchetto = new DatagramPacket(buffer, buffer.length, inetAddressIndex, portaIndexServer );
            serverUDP.send(pacchetto);
            serverUDP.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String...args){ StorageServer ss = new StorageServer("localhost");}


}
