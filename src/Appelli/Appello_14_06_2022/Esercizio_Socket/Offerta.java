package Appelli.Appello_14_06_2022.Esercizio_Socket;

public class Offerta {

    private String settore, ruolo, tipo, retribuzione;

    public Offerta(String s, String r, String t, String ret){
        settore = s;
        ruolo = r;
        tipo = t;
        retribuzione = ret;
    }

    public String getRetribuzione() {
        return retribuzione;
    }

    public String getRuolo() {
        return ruolo;
    }

    public String getSettore() {
        return settore;
    }

    public String getTipo() {
        return tipo;
    }
}
