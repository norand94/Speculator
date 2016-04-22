package ru.stupnikov.application.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by rodion on 25.01.16.
 */


public class Valuta implements Serializable {
    public String name;
    public double valueRUB; // есди это рубль, то равно =1

    public Valuta(String name, double value){
        this.name = name; this.valueRUB = value;
    }

}
