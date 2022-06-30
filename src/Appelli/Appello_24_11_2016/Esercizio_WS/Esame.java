package Appelli.Appello_24_11_2016.Esercizio_WS;

public class Esame {

    private String nome;
    private Data data;

    public Esame(String n, Data d){
        nome = n;
        data = d;
    }

    public String getNome() {
        return nome;
    }

    public Data getData() {
        return data;
    }

    public String toString(){
        return "Esame: "+nome+" da svlgersi il: "+data.toString();
    }

    public int hashCode(){
        return ("Esame: "+nome+" da svlgersi il: "+data.toString()).hashCode();
    }

    public boolean equals(Object o){
        if(!(o instanceof Esame)) return false;
        Esame e = (Esame) o;
        if(e.data.equals(data)) return true;
        return false;
    }
}
