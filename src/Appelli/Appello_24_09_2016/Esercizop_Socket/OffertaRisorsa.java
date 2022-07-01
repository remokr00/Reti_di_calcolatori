package Appelli.Appello_24_09_2016.Esercizop_Socket;

public class OffertaRisorsa {

    private String nome, tipo, descrizione;

    public OffertaRisorsa(String nome, String tipo, String descrizione){
        this.nome = nome;
        this.tipo = tipo;
        this.descrizione = descrizione;
    }

    public String getNome(){ return nome; }
    public String getTipo(){ return tipo; }
    public String getDescrizione() { return descrizione;}
}
