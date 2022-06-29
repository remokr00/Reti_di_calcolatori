package Esercitazione_2;

import java.io.Serializable;

public class Studente implements Serializable {

    private int mat;
    private String nome, cognome, CDL;

    public Studente(int m, String n, String c, String l){
        mat=m;
        nome=n;
        cognome=c;
        CDL=l;
    }

    public String toString(){
        return "Lo studente ha matricola: "+mat+", si chiama "+nome+" "+cognome+" e frequenta "+CDL;
    }
}
