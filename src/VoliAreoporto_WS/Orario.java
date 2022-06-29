package VoliAreoporto_WS;

public class Orario {

    private int ore, minuti;

    public Orario(int o, int m){
        ore = o;
        minuti = m;
    }

    public int getOre() {
        return ore;
    }

    public int getMinuti() {
        return minuti;
    }

    public int hashCode(){
        return (ore+":"+minuti).hashCode();
    }

    public boolean equals(Object o){
        if( ! (o instanceof Orario )) return false;
        Orario ora = (Orario) o;
        if((ora.ore == ore) && (minuti == ora.minuti)) return true;
        return false;
    }
}
