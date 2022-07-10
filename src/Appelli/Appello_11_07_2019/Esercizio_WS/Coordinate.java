package Appelli.Appello_11_07_2019.Esercizio_WS;

import java.io.Serializable;

public class Coordinate implements Serializable {

    private double latitudine, longitudine;

    public Coordinate(double lat, double longi){
        latitudine = lat;
        longitudine = longi;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }
}
