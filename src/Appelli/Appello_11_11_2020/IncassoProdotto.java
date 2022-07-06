package Appelli.Appello_11_11_2020;

public class IncassoProdotto {

    private String nome, importo;

    public IncassoProdotto(String nome, String importo){
        this.nome = nome;
        this.importo = importo;
    }

    public String getNome() {
        return nome;
    }

    public String getImporto() {
        return importo;
    }

    public int hashCode(){
        return ("Prodotto: "+nome+", "+importo).hashCode();
    }

    public String toString(){
        return "Prodotto: "+nome+", "+importo;
    }

    public boolean equals(Object o){
        if(!(o instanceof IncassoProdotto)) return false;
        IncassoProdotto i = (IncassoProdotto) o;
        if(i.nome.equals(nome) && i.importo.equals(importo)) return true;
        return false;
    }

}
