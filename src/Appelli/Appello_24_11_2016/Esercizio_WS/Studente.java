package Appelli.Appello_24_11_2016.Esercizio_WS;

public class Studente {

    private String nome, cognome;
    private int matricola;

    public Studente(String n, String c, int m){
        nome = n;
        cognome = c;
        matricola = m;
    }

    public String getCognome() {
        return cognome;
    }

    public int getMatricola() {
        return matricola;
    }

    public String getNome() {
        return nome;
    }

    public int hashCode(){
        return ("Studente: "+nome+" "+cognome+" "+matricola).hashCode();
    }

    public boolean equals(Object o){
        if(!(o instanceof Studente)) return false;
        Studente s = (Studente) o;
        if(s.matricola==matricola) return true;
        return false;
    }

    public String toString(){
        return "Studente: "+nome+" "+cognome+" "+matricola;
    }
}
