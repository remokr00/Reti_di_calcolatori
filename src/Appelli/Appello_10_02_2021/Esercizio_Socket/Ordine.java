package Appelli.Appello_10_02_2021.Esercizio_Socket;

public class Ordine {

    private int data;
    private int idAgente, idacquirente, quantita;
    private String prodotto;

    public Ordine(int id1, int id2, int quantita, int data, String p){
        idacquirente = id1;
        idAgente = id2;
        this.data = data;
        this.quantita = quantita;
        prodotto = p;
    }

    public int getData() {
        return data;
    }

    public int getIdacquirente() {
        return idacquirente;
    }

    public int getIdAgente() {
        return idAgente;
    }

    public int getQuantita() {
        return quantita;
    }

    public String getProdotto() {
        return prodotto;
    }
}
