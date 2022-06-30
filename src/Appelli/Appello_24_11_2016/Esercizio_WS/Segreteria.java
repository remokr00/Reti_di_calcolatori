package Appelli.Appello_24_11_2016.Esercizio_WS;

import java.util.HashMap;
import java.util.Map;

public class Segreteria {

    /*
    In questa mappa ho come chiave un esame e come valore gli studenti che anno partecipato
    gli studenti che hanno partecipato sono identificati da un hashMap che ha come chiave lo studente
    e come valore il voto associato allo studente
     */
    private HashMap<Esame, HashMap<Studente, Integer>> esiti;

    public Segreteria(HashMap<Esame, HashMap<Studente, Integer>> e){
        esiti = e;
    }

    public int votoStudente(String nomeEsame, int matricola){
        //Vedo se si è svolto l'esame
        boolean trovatoEsame = false;
        Integer risultato = -1;
        for(Map.Entry<Esame, HashMap<Studente, Integer>> entry : esiti.entrySet()){
            if(entry.getKey().getNome().equals(nomeEsame)){//se si è svolto vedo se lo studente ha partecipato
                trovatoEsame = true;
                HashMap<Studente, Integer> partecipanti = entry.getValue();
                for(Map.Entry<Studente, Integer> studente : partecipanti.entrySet()){
                    if(studente.getKey().getMatricola() == matricola){ //si da per scontato che lo studente abbia preso parte all'esame
                        return studente.getValue();
                    }
                }
            }
        }
        //non esiste l'esame
        if(!trovatoEsame){
            System.out.println("Esame non ancora svolto");
            //non è stato modificato il risultato quindi restituisco -1
            return risultato;
        }
        //restituisco il voto preso dallo studente
        return risultato;
    }

    public String esameGiorno(Data data){
        //per ogni esame verifico se corrisponde la data
        boolean trovato = false;
        String risultato = "";
        for(Map.Entry<Esame, HashMap<Studente, Integer>> entry : esiti.entrySet()){
            if(entry.getKey().getData().equals(data)){
                //ho trovato l'esame
                trovato = true;
                risultato = entry.getKey().getNome();
            }
        }
        if(!trovato){
            //l'esame non è stato svolto
            return risultato; //ovvero la stringa vuota
        }

        return risultato;
    }
}
