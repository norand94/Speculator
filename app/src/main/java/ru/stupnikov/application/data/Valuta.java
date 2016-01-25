package ru.stupnikov.application.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by rodion on 25.01.16.
 */
public class Valuta implements Serializable {
    public String name;
    public double value;
    public Date date;

    public Valuta(String name, double value, Date date){
        this.name = name; this.value = value; this.date =date;
    }

}
