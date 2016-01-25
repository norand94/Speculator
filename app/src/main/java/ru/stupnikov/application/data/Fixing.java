package ru.stupnikov.application.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by rodion on 25.01.16.
 */
public class Fixing implements Serializable {

    public Date date = new Date();
    public double value = 0;
    public String description = "";
    public String category ="";
    public String subcategory ="";

    public Fixing(Date date, double value, String description, String category, String subcategory){
        this.date =date; this.value =value; this.description = description;
        this.category =category;
        this.subcategory =subcategory;
    }
}
