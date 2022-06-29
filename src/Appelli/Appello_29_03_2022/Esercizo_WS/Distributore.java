package Appelli.Appello_29_03_2022.Esercizo_WS;

public class Distributore {

    private String partitaIVA;
    private String ragioneSociale;
    private String regione;
    private double prezzoDiesel;
    private double prezzoBenzina;

    public Distributore(String pI, String rs, String r, double pd, double pb){
        partitaIVA = pI;
        ragioneSociale = rs;
        regione = r;
        prezzoDiesel = pd;
        prezzoBenzina = pb;
    }

    public String getPartitaIVA(){ return  partitaIVA; }
    public String getRagioneSociale(){ return ragioneSociale; }
    public String getRegione(){ return regione; }
    public double getPrezzoDiesel(){ return  prezzoDiesel; }
    public double getPrezzoBenzina() { return prezzoBenzina; }

    public int hashCode(){
        return partitaIVA.hashCode();
    }

    public boolean equals(Object o){
        if(!(o instanceof Distributore)) return false;
        Distributore d = (Distributore) o;
        if(d.partitaIVA.equals(partitaIVA)) return  true;
        return false;
    }

    public String toString(){
        return partitaIVA+", "+ragioneSociale+", "+regione+", "+prezzoDiesel+", "+prezzoBenzina;
    }

}

