package Appelli.Appello_18_03_2021.Esercizio_WS;

import java.util.ArrayList;
import java.util.Arrays;

public class EsamiListType {

    private ArrayList<String> esami;

    public EsamiListType (){
        esami = new ArrayList<>();
    }

    public ArrayList<String> getEsami() {
        return esami;
    }

    public String toString(){
        String ris = "";
        for(String esame : esami){
            ris += esame;
        }
        return ris;
    }

    public int hashCode(){
        return esami.hashCode();
    }

    public boolean equals(Object o){
        if(!( o instanceof  EsamiListType)) return false;
        EsamiListType e = (EsamiListType) o;
        if(e.getEsami().size()!=esami.size()) return false;
        for(int i = 0; i<esami.size(); i++){
            if(!(e.getEsami().get(i).equals(esami.get(i)))) {
                return false;
            }
        }
        return true;
    }
}
