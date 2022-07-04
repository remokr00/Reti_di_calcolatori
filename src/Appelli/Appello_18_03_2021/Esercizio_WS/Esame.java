package Appelli.Appello_18_03_2021.Esercizio_WS;

public class Esame {
    //classe aggiuntiva per facilitarmi lo sviluppo del web service
    private String codice, nome;
    private int voto;

    public Esame(String c, String n, int v){
        codice = c;
        nome = n;
        voto = v;
    }

    public String getCodice(){
        return codice;
    }

    public String getNome() {
        return nome;
    }

    public int getVoto() {
        return voto;
    }

    public int hashCode(){
        return ("Esame :"+nome+" "+codice+" "+voto).hashCode();
    }

    public String toString(){
        return "Esame :"+nome+" "+codice+" "+voto;
    }
}

