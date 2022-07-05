package Appelli.Appello_14_06_2022.Esercizio_WS;

public class Citta {

    private String nome, provincia, regione;

    public Citta(String n, String p, String r){
        nome = n;
        provincia = p;
        regione = r;
    }

    public String getNome() {
        return nome;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getRegione() {
        return regione;
    }

    public int hashCode(){
        return ("Città: "+nome+" "+provincia+" "+regione).hashCode();
    }

    public String toStirng(){
        return "Città: "+nome+" "+provincia+" "+regione;
    }

    public boolean equals(Object o){
        if(!(o instanceof Citta)) return false;
        Citta c = (Citta) o;
        if(c.nome.equals(nome) && c.provincia.equals(provincia) && c.regione.equals(regione)) return true;
        return false;
    }
}
