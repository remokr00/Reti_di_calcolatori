package Appelli.Appello_18_03_2021.Esercizio_WS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EsamiService {

    private HashMap<Studente, ArrayList<Esame>> database = new HashMap<>();

    public EsamiListType esamiStudente(String matricola) {
         boolean trovato = false;
         EsamiListType lista = new EsamiListType();
         for(Map.Entry<Studente, ArrayList<Esame>> entry : database.entrySet()){
             if(entry.getKey().getMatricola().equals(matricola)){
                 trovato = true;
                 //prendo la lista degli esami associati
                 ArrayList<Esame> esami = entry.getValue();
                 for(Esame esame : esami){
                     lista.getEsami().add(esame.getCodice());
                 }
             }
         }
         if(!trovato){
             System.out.println("Studente non registrato");
             return null;
         }
         return lista;
    }

    public StudentiListType StudentiPerEsame(String codice, int voto){
        StudentiListType studenti = new StudentiListType();
        boolean trovato = false;
        for(Map.Entry<Studente, ArrayList<Esame>> entry : database.entrySet()){
            ArrayList<Esame> esami = entry.getValue();
            for(Esame esame : esami){
                if(esame.getCodice().equals(codice)){
                    if(esame.getVoto()>=voto){
                        trovato = true;
                        studenti.getStudenti().add(entry.getKey());
                    }
                }
            }
        }
        if(!trovato){
            System.out.println("Nessuno studente soddisfa òa richiesta");
            return null;
        }
        return studenti;
    }

}
