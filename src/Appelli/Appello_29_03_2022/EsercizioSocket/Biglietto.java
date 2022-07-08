package Appelli.Appello_29_03_2022.EsercizioSocket;

public class Biglietto {

    private String nomeLotteria;
    private int numero;

    public Biglietto(String nome){
        nomeLotteria = nome;
        numero = (int) (Math.random()*10000)+1; //non so come si calcola senza ripetizioni
    }

    public Biglietto(int numero){
        nomeLotteria = null;
        this.numero = numero; //non so come si calcola senza ripetizioni
    }

    public int getNumero() {
        return numero;
    }

    public String getNomeLotteria() {
        return nomeLotteria;
    }
}
