package Appelli.Appello_10_02_2021.Esercizio_WS;

import java.io.Serializable;
import java.util.HashMap;

public class Risultato implements Serializable {

    public int idImpianto, quantita;

    public Risultato(int idImpianto, int quantita){
        this.idImpianto = idImpianto;
        this.quantita = quantita;
    }
}
