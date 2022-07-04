package Appelli.Appello_20_01_2021.Esercizio_WS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArrayOfData {

    private List<Data> date;

    public ArrayOfData(){
        date = new ArrayList<>();
    }
    public List<Data> getDate() {
        return date;
    }

    public boolean equals(Object o){
        if(!(o instanceof ArrayOfData)) return false;
        ArrayOfData a = (ArrayOfData) o;
        if(a.getDate().size() != date.size()) return false;
        for(int i =0; i< a.getDate().size(); i++){
            if(!a.getDate().get(i).equals(date.get(i)))
                return false;
        }
        return true;
    }

    public int hashCode(){
        int ris = 0;
        for(Data d : date){
            ris+=d.hashCode();
        }
        return ris;
    }

    public String toString(){
        String ris = "" ;
        for(Data d : date){
            ris += d.toString()+"; ";
        }

        return ris;
    }
}
