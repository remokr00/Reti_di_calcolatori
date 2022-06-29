package VoliAreoporto_WS;

public class Volo {

    private String citta;
    private Orario orario;
    private Data data;
    private String voloId;

    public Volo(String c, Data d, Orario o, String id){
        citta = c;
        data = d;
        orario = o;
        voloId = id;
    }

    public String getCitta(){
        return citta;
    }

    public Data getData(){
        return data;
    }

    public Orario getOrario(){
        return orario;
    }

    public String getVoloId(){
        return voloId;
    }

    public int hashCode(){
        return (voloId).hashCode();
    }

    public boolean equals(Object o){
        if( ! (o instanceof Volo)) return false;
        Volo v = (Volo) o;
        if(v.voloId == voloId) return true;
        return false;
    }


}
