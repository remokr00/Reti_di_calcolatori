package Appelli.Appello_14_06_2022.Esercizio_WS;

import java.util.ArrayList;

public class ListCitta {

    private ArrayList<Citta> list;

    public ListCitta(){
        list = new ArrayList<>();
    }

    public ArrayList<Citta> getList() {
        return list;
    }

    public String toString(){
        String ris = "";
        for(Citta c : list){
            ris += c.toStirng();
        }
        return ris;
    }

    public int hashCode(){
        return list.hashCode();
    }

    public boolean equals(Object o){
        if(!(o instanceof ListCitta)) return false;
        ListCitta c = (ListCitta) o;
        if(!(c.getList().size() == list.size())) return false;
        for(int i = 0; i< list.size(); i++){
            if(!(c.getList().get(i).equals(list.get(i)))){
                return false;
            }
        }
        return true;
    }


}
