package Appelli.Appello_20_01_2021.Esercizio_Socket;

public class Misura {

    private int id;
    private double valore;
    private long tempo;

    public Misura(int i){
        id = i;
        valore = Math.random();
        tempo = System.currentTimeMillis();
    }

    public Misura(int i, double v){
        id = i;
        valore = v;
        tempo = System.currentTimeMillis();
    }

    public double getValore() {
        return valore;
    }

    public int getId() {
        return id;
    }

    public long getTempo() {
        return tempo;
    }
}
