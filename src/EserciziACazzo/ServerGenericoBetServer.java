package EserciziACazzo;

public class ServerGenericoBetServer {
    //inizializzo le variabili utili
    private int var1;
    private int var2;
    private int varN;

    //creo il costruttore Modalità 1:
    //avvio i thread che gestiscono richiesta e risposta nel costruttore
    public ServerGenericoBetServer(int param1, int param2, int paramN){
        try{
            //inizializzo le variabili e chiamo i thread ai quali eventualmente passo parametri utili
            //new threadRichiesta.start(var1,var2,varN);
            //new threadRisposta.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //creo il costruttore Modalità 2:
    //chiamo un metodo avvia che esegue l'inizializzazione dei socket e la chiamata dei thread

    public ServerGenericoBetServer(int param1, int paramN){
        //inizializzo le altre variabili
        avvia(param1, paramN);
    }

    private void avvia(int param1, int paramN){
        try{
            //inizializzo i socket e chiamo i thread ai quali eventualmente passo parametri utili
            //new threadRichiesta.start(var1,var2,varN);
            //new threadRisposta.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //creo le classi thread come inner class
    class threadRichiesta extends Thread{

        //se al thread ho passato dei parametri allora creo un costruttore
        public threadRichiesta(int param1, int paramN){
            try {
                //inziializzo eventuali socket utili
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //creo il metodo run
        public void run(){
            //eseguo le operazioni di lettura della richiesta
            // ed eventuale invio ad altri server
        }
    }

    class threadRisposta extends Thread{

        //creo il metodo run
        public void run(){
            //eseguo le operazioni di lettura della richiesta
            // ed eventuale invio ad altri server
        }
    }

    //creo un main per vviare il server allo stesso modo del client
    public static void main(String...args){
        try{
            //qui creo un client e ririchiamo i metodi
            //le seguenti variabili e i valori assegnati sono a scopo di esempio
            int param1=1;
            int param2=2;
            int paramN=3;
            ServerGenericoBetServer s= new ServerGenericoBetServer(param1, param2, paramN);
            //basta creare un nuovo serve senza richiamare ulteriori metodi perché poi
            //questo sarà sempre in esecuzione
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
