package ru.stupnikov.application.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by rodion on 25.01.16.
 */
public class ValutaDate implements Serializable {
    public Date date = new Date();
    public ArrayList<Valuta> listValuta = new ArrayList<Valuta>();
    public ValutaDate(ArrayList<Valuta> listValuta, Date date){
        this.listValuta = listValuta;
    }
}
