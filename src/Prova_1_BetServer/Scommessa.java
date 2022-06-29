package Prova_1_BetServer;

import java.net.InetAddress;

public class Scommessa {

    private int ID_scommessa, numeroCavallo;
    private long puntata;
    private InetAddress scommettitore;
    private static int prossimo = 0;

    public Scommessa(int numeroCavallo, long puntata, InetAddress scommettitore) {

        this.numeroCavallo=numeroCavallo;
        this.puntata=puntata;
        this.scommettitore=scommettitore;

    }//costruttore

    public int getID(){ return  ID_scommessa; }
    public int getNumeroCavallo(){ return numeroCavallo; }
    public InetAddress getScommettitore(){ return  scommettitore; }
    public long getPuntata(){ return  puntata; }

    public boolean equals(Object o){
        if(!(o instanceof Scommessa)) return false;
        Scommessa scommessa = (Scommessa) o;
        return numeroCavallo == scommessa.numeroCavallo;
    }


}
