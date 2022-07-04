package Appelli.Appello_Maggio_2020.Esercizio_WS;

public class Ospedale {

    private int codice, numRicoverati, numPostiLetto;
    private String città;

    public Ospedale(int c, String citt, int n1, int n2){
        codice = c;
        numPostiLetto = n2;
        numRicoverati = n1;
        città = citt;
    }

    public int getCodice() {
        return codice;
    }

    public int getNumPostiLetto() {
        return numPostiLetto;
    }

    public int getNumRicoverati() {
        return numRicoverati;
    }

    public String getCittà() {
        return città;
    }

    public String toString(){
        return "Ospedale: "+codice+", situato a"+città+", numero pazienti pari a "+numRicoverati+" num posti pari a"+numPostiLetto;
    }

    public int hashCode(){
        return ("Ospedale: "+codice+", situato a"+città+", numero pazienti pari a "+numRicoverati+" num posti pari a"+numPostiLetto).hashCode();
    }

    public boolean equals(Object o){
        if(!( o instanceof Ospedale)) return false;
        Ospedale os = (Ospedale) o;
        if(os.codice == codice) return true;
        return false;
    }

}

