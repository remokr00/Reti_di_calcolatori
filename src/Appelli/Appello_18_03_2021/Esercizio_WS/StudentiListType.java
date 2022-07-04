package Appelli.Appello_18_03_2021.Esercizio_WS;

import java.sql.Array;
import java.util.ArrayList;

public class StudentiListType {

    private ArrayList<Studente> studenti;

    public StudentiListType(){
        studenti = new ArrayList<>();
    }

    public ArrayList<Studente> getStudenti() {
        return studenti;
    }

    public String toString(){
        String ris = "";
        for(Studente studente : studenti){
            ris += studente.toString();
        }
        return ris;
    }

    public int hashCode(){
        return studenti.hashCode();
    }

    public boolean equals(Object o){
        if(!( o instanceof  StudentiListType)) return false;
        StudentiListType s = (StudentiListType) o;
        if(s.getStudenti().size()!=studenti.size()) return false;
        for(int i = 0; i<studenti.size(); i++){
            if(!(s.getStudenti().get(i).equals(studenti.get(i)))) {
                return false;
            }
        }
        return true;
    }
}
