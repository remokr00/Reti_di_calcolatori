package Appelli.Appello_20_06_2020.EsercizioSocket;

import VoliAreoporto_WS.Data;

public class Dato {

    private String grandezza;
    private double valore;
    private long tempoAcquisizione;

    public Dato(String g, double v){
        grandezza = g;
        valore = v;
        tempoAcquisizione = System.currentTimeMillis();
    }

    public String getGrandezza(){ return grandezza; }
    public double getValore() {return valore; }
    public long getTempoAcquisizione(){ return tempoAcquisizione; }
}
