package ru.stupnikov.application.adapter;

import java.util.Date;

/**
 * Created by rodion on 16.02.16.
 */
public class ValutaContainer  {
    public String name;
    public int icon;
    public double valueRUB_1; // есди это рубль, то равно =1
    public double valueRUB_2;



/*
     public boolean visible;
    public Date date_value1;
    public Date date_value2;
*/

    public ValutaContainer(String name, int icon, double valueRUB_1, double valueRUB_2){
        this.name =name; this.icon =icon; this.valueRUB_1=valueRUB_1; this.valueRUB_2=valueRUB_2;
    }


    public String getValue1_toString(){
        return String.valueOf(valueRUB_1);
    }

    public String getValue2_toString(){
        return String.valueOf(valueRUB_2);
    }

  /*  public void setVisible(boolean visible){this.visible = visible;}
    public boolean getVisible(){return  visible; }*/
}
