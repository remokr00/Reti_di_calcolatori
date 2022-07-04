package Appelli.Appello_18_03_2021.Esercizio_WS;

public class Studente {

    private String matricola, nome , cognome;

    public Studente(String m, String n, String c){
        matricola = m;
        nome = n;
        cognome = c;
    }

    public String getNome() {
        return nome;
    }

    public String getMatricola() {
        return matricola;
    }

    public String getCognome() {
        return cognome;
    }

    public int hashCode(){
        return ("Studente: "+nome+" "+cognome+" "+matricola).hashCode();
    }

    public boolean equals(Object o){
        if(!( o instanceof  Studente)) return false;
        Studente s = (Studente) o;
        if(matricola != s.matricola) return false;
        return true;
    }

    public String toString(){
        return "Studente: "+nome+" "+cognome+" "+matricola;
    }
}