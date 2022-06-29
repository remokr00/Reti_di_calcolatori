package Appelli.Appello_29_03_2022.Esercizo_WS;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class GestoreRichiesta {

    //per ogni regione salvo la lista di distributori
    private HashMap<String, LinkedList<Distributore>> database;

    public GestoreRichiesta(){
        database = new HashMap<>();
    }

    public double minPrezzoBenzina(String regione){
        //se non esiste la regione restituisco -1
        if(! database.containsKey(regione) ){
            return -1;
        }
        //prendo la lista dei distributori per la regione che mi interessa
        LinkedList<Distributore> distributori = database.get(regione);
        double min = distributori.get(0).getPrezzoBenzina();
        for(Distributore d : distributori){
            if(min < d.getPrezzoBenzina())
                min = d.getPrezzoBenzina();
        }

        return min;
    }

    public String regioneMinMedia(){
        String ris = "";
        double min = 0;
        for(Map.Entry<String, LinkedList<Distributore>> entry : database.entrySet()){
            //per ogni regione prendo la list dei distributori
            LinkedList<Distributore> distributori = entry.getValue();
            double somma = 0;
            int cont = 0;
            for(Distributore d : distributori){
                cont++;
                somma+=d.getPrezzoDiesel();
            }
            //calcolo la media del prezzo dei distributori
            double media = somma/cont;
            if(media < min){
                min = media;
                ris = entry.getKey();
            }
        }
        return ris;
    }


}
