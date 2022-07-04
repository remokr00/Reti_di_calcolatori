package Appelli.Appello_20_01_2021.Esercizio_WS;

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

    public int compareTo(Data d){
        if(d.equals(this)) return 0;
        if(d.anno<this.anno) return -1;
        if(d.mese<this.mese) return -1;
        if(d.giorno<this.giorno) return -1;
        return 1;
    }
}
