package Appelli.Appello_24_09_2016.Esercizio_WS;

public class Data {

    private int giorno,mese,anno;

    public Data(int g, int m, int a){
        giorno = g;
        mese = m;
        anno = a;
    }

    public int getAnno() {
        return anno;
    }

    public int getGiorno() {
        return giorno;
    }

    public int getMese() {
        return mese;
    }

    public int hashCode(){
        return (""+giorno+"-"+mese+"-"+anno).hashCode();
    }

    public String toString() {
        return ""+giorno+"-"+mese+"-"+anno;
    }

    public boolean equals(Object o){
        if(! (o instanceof Data )){
            return false;
        }
        Data d = (Data) o;
        if( d.giorno==giorno && d.mese==mese && d.anno==anno) return true;
        return false;
    }
}
