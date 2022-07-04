package Appelli.Appello_20_01_2021.Esercizio_WS;

public class Negozio {

    private String partitaIVA;
    private String provincia;

    public Negozio(String pi, String p){
        partitaIVA = pi;
        provincia = p;
    }

    public String getPartitaIVA() {
        return partitaIVA;
    }

    public String getProvincia() {
        return provincia;
    }

    public boolean equals(Object o){
        if(!(o instanceof  Negozio)) return false;
        Negozio n = (Negozio) o;
        if(n.partitaIVA.equals(partitaIVA)) return true;
        return false;
    }

    public int thashCode(){
        return ("Negozio: "+partitaIVA+"," +provincia).hashCode();
    }

    public String toString(){
        return "Negozio: "+partitaIVA+"," +provincia;
    }
}
