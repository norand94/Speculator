package ru.stupnikov.application.db_data;


import java.util.Date;

/**
 * Created by rodion on 22.04.16.
 */

public class Fixing  {

    public Date date = new Date();
    //public boolean isProfit;
    public double value = 0;
    public String description = "";
    private Long id_category;
    private Long id_subcategory;
    private Long id_wallet;

    public Fixing(){

    }
    public Fixing(Date date, double value, String description, Long id_category, Long id_subcategory, Long id_wallet ){
        this.date =date; this.value =value; this.description =description;
        this.id_category =id_category;
        this.id_subcategory =id_subcategory;
        this.id_wallet =id_wallet;
    }

}
