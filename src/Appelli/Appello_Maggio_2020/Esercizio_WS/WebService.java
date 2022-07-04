package Appelli.Appello_Maggio_2020.Esercizio_WS;

import java.util.HashMap;
import java.util.Map;

public class WebService {

    private HashMap<String, Ospedale[]> databse;

    public Ospedale maxOspedale(String citta){
        if(databse.containsKey(citta)){
            Ospedale[] ospedali = databse.get(citta);
            Ospedale ris = null;
            int max = 0;
            for(int i = 0; i < ospedali.length; i++){
                if(ospedali[i].getNumPostiLetto()>max){
                    max = ospedali[i].getNumPostiLetto();
                    ris = ospedali[i];
                }
            }
            return ris;
        }
        return new Ospedale(-1, "err", -1, -1);
    }

    public String cittaRatio(){
         String ris = null;
         double max = 0;
         for(Map.Entry<String,Ospedale[]> entry : databse.entrySet()){
             int sommaPazienti = 0;
             int sommaPosti = 0;
             double ratio = 0;
             Ospedale[] ospedali = entry.getValue();
             for(int i = 0; i< ospedali.length; i++){
                 sommaPazienti += ospedali[i].getNumRicoverati();
                 sommaPosti += ospedali[i].getNumPostiLetto();
             }
             ratio = sommaPazienti/sommaPosti;
             if(ratio>max){
                 max = ratio;
                 ris = entry.getKey();
             }
         }
         return ris;
    }
}
