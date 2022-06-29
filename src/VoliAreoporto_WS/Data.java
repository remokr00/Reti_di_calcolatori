package VoliAreoporto_WS;

public class Data {

    private int giorno, mese, anno;

    public Data(int g, int m, int a){
        giorno = g;
        mese = m;
        anno = a;
    }

    public int getGiorno(){ return
            giorno;
    }

    public int getMese() {
        return mese;
    }

    public int getAnno() {
        return anno;
    }

    public int hashCode(){
        return (giorno+"-"+mese+"-"+anno).hashCode();
    }

    public boolean equals(Object o){
        if(! (o instanceof Data)) return false;
        Data d = (Data) o;
        if((giorno == d.giorno) && (mese == d.mese) && (anno == d.anno)) return true;
        return false;
    }

    public String toString(){
        return giorno+"-"+mese+"-"+anno;
    }
}
