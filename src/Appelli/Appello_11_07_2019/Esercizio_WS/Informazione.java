package Appelli.Appello_11_07_2019.Esercizio_WS;

import java.io.Serializable;

public class Informazione implements Serializable {

    private String condizione;
    private double temperaturaMin, temperaturaMax;

    public Informazione(String c, double tm, double tM){
        condizione = c;
        temperaturaMin = tm;
        temperaturaMax = tM;
    }

    public double getTemperaturaMax() {
        return temperaturaMax;
    }

    public double getTemperaturaMin() {
        return temperaturaMin;
    }

    public String getCondizione() {
        return condizione;
    }

}
