package Appelli.Appello_11_07_2019.Esercizio_WS;

import Appelli.Appello_14_06_2022.Esercizio_WS.Citta;

import java.util.ArrayList;
import java.util.HashMap;

public class Stato {

    private String nome;
    private HashMap<Coordinate, String> citta;

    public Stato(String n, HashMap<Coordinate, String> c){
        nome = n;
        citta = c;
    }

    public String getNome() {
        return nome;
    }

    public HashMap<Coordinate, String> getCitta() {
        return citta;
    }
}
