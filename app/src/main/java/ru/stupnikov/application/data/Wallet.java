package ru.stupnikov.application.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rodion on 21.01.16.
 */
public class Wallet implements Serializable {

    public Wallet() {}
    public Wallet(String name) {this.name = name;}
    public Wallet(String name, String valuta, double value, int position,
                  ArrayList<String> listConvertableValuta, ArrayList<Fixing> listFixings) {
        this.name =name; this.valuta = valuta; this.value =value; this.position =position;
        this.listConvertibleValuta = listConvertableValuta;
        this.listFixings = listFixings;
    }

    public String name = "default";
    public String valuta = "RUB";
    public double value = 0;
    public int position = 0;
    public ArrayList<String> listConvertibleValuta = new ArrayList<String>();
    public ArrayList<Fixing>listFixings =new ArrayList<Fixing>();
}
