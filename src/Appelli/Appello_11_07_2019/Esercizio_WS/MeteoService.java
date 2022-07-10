package Appelli.Appello_11_07_2019.Esercizio_WS;

import Appelli.Appello_14_06_2022.Esercizio_WS.Citta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MeteoService {

    private HashMap<Stato, HashMap<Coordinate, Informazione>> database;

    public Informazione meteoCoordinate(Coordinate coordinate){
        Informazione ris = null;
        for(Map.Entry<Stato, HashMap<Coordinate, Informazione>> entry : database.entrySet()){
            HashMap<Coordinate, Informazione> citta = entry.getValue();
            if(citta.containsKey(coordinate)){
               ris = citta.get(coordinate);
            }
        }
        return ris;
    }

    public String maxDifferenzaTemperatura(String nomeStato){
        String ris = null;
        for(Map.Entry<Stato, HashMap<Coordinate, Informazione>> entry : database.entrySet()){
            if(entry.getKey().getNome().equals(nomeStato)){
                HashMap<Coordinate, String> citta = entry.getKey().getCitta();
                HashMap<Coordinate, Informazione> coordinate = entry.getValue();
                double max = 0;
                for(Map.Entry<Coordinate, Informazione> entry1 : coordinate.entrySet()){
                    Informazione info = entry1.getValue();
                    double diff = info.getTemperaturaMax()-info.getTemperaturaMin();
                    if(diff > max){
                        max = diff;
                        ris = citta.get(coordinate);
                    }
                }

            }
        }
        return ris;
    }


}
