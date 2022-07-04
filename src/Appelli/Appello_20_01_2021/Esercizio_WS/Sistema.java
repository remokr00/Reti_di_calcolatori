package Appelli.Appello_20_01_2021.Esercizio_WS;

import java.util.HashMap;
import java.util.Map;

public class Sistema {

    private HashMap<Negozio, HashMap<Data, Double>> database;


    public String guadagnoDaA(String provincia, Data dataFine){
        double max =0;
        String ris="";
        boolean trovato = false;
        for(Map.Entry<Negozio, HashMap<Data, Double>> entry : database.entrySet()){
            if(provincia.equals(entry.getKey().getProvincia())){
                trovato = true;
                HashMap<Data,Double> incassiGiornalieri = entry.getValue();
                double somma = 0;
                for(Map.Entry<Data,Double> entry1 : incassiGiornalieri.entrySet()){
                    if(entry1.getKey().compareTo(dataFine)<0){
                        somma += entry1.getValue();
                    }
                }
                if(somma > max){
                    max = somma;
                    ris = entry.getKey().getPartitaIVA();
                }
            }
        }
        if(!trovato){
            System.out.println("Nessun negozio appartiene a questa provincia");
            return null;
        }
        return ris;
    }


    public ArrayOfData dateGuadagno(String pIva, double cifra){
        ArrayOfData ris = new ArrayOfData();
        boolean trovato = false;
        for(Map.Entry<Negozio, HashMap<Data, Double>> entry: database.entrySet()){
            if(entry.getKey().getPartitaIVA().equals(pIva)){
                trovato = true;
                HashMap<Data, Double> valore = entry.getValue();
                for(Map.Entry<Data, Double> entry1 : valore.entrySet()){
                    if(entry1.getValue()>=cifra){
                        ris.getDate().add(entry1.getKey());
                    }
                }
            }
        }
        if(!trovato){
            System.out.println("Il negozio non è registrato");
            return null;
        }
        return ris;
    }




}
