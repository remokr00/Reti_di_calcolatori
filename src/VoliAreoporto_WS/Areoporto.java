package VoliAreoporto_WS;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Areoporto {

    private HashMap<Data, HashMap<String, LinkedList<Volo>>> database;

    public Areoporto(){
        database = new HashMap<>();
    }

    public String primoVolo(String citta, Data data){
        if( ! database.containsKey(data) || ! database.get(data).containsKey(citta))
            return "Nessun aereo";
        return database.get(data).get(citta).getFirst().getVoloId();
    }

    public Orario orarioVolo(String voloId, Data data){
        LinkedList<Volo> tmp;
        if(database.containsKey(data)){
            for(Map.Entry<String, LinkedList<Volo>> entry : database.get(data).entrySet()){
                tmp = entry.getValue();
                for (Volo volo2 : tmp){
                    if(volo2.getVoloId().equals(voloId))
                        return volo2.getOrario();
                }
            }
        }

        return new Orario(-1, -1);
    }
}
