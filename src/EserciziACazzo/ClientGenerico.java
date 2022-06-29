package EserciziACazzo;

public class ClientGenerico {

    //inserisco le variabili di istanza utili
    private int var1;
    private int var2;
    private int varN;

    //creo il costruttore
    public ClientGenerico(int param1, int param2, int paramN){
        //inizializzo le variabili
    }

    public void metodo1(){
        //gestisco la prima parte del client quindi
        //per esempio l'invio di una richiesta
    }

    public void metodo2(){
        //gestisco la seconda parte del client quindi
        //per esempio la lettura di una risposta
    }

    public static void main(String...args){
        try{
            //qui creo un client e ririchiamo i metodi
            //le seguenti variabili e i valori assegnati sono a scopo di esempio
            int param1=1;
            int param2=2;
            int paramN=3;
            ClientGenerico c = new ClientGenerico(param1, param2, paramN);
            c.metodo1();
            c.metodo2();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
