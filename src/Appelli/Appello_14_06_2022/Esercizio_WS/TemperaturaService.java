package Appelli.Appello_14_06_2022.Esercizio_WS;

import java.util.HashMap;
import java.util.Map;

public class TemperaturaService {

    //suppongo che nell'array di dpouble in pos 0 ci sia la temperatura minima e in pos 1 la massima
    private HashMap<Citta, Double[]> temperature;

    public Citta maxDiff(String regione, String provincia){
        double max = 0;
        Citta ris = null;
        for(Map.Entry<Citta, Double[]> entry : temperature.entrySet()){
            Citta cittaCorrente = entry.getKey();
            if(cittaCorrente.getRegione().equals(regione) && cittaCorrente.getProvincia().equals(provincia)){
                //prendo le temperature corrispondenti
                Double[] temp = entry.getValue();
                double differenza = temp[1]-temp[0];
                if(differenza > max){
                    max = differenza;
                    ris = cittaCorrente;
                }
            }
        }
        return ris;
    }

    public ListCitta soglia(double t, String regione){
        ListCitta ris = new ListCitta();
        for(Map.Entry<Citta, Double[]> entry : temperature.entrySet()){
            Citta cittaCorrente = entry.getKey();
            if(cittaCorrente.getRegione().equals(regione)){
                Double[] temp = entry.getValue();
                if(temp[1] > t){
                    ris.getList().add(cittaCorrente);
                }
            }
        }
        return ris;
    }

}
