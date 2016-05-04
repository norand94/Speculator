package ru.stupnikov.application.db_data;


import java.util.List;

/**
 * Created by rodion on 22.04.16.
 */
public class Wallet{
    public String name = "default";
    public String valuta = "RUB";
    public double value = 0;

    public Wallet(){

    }
    public Wallet(String name, String valuta, double value){
        this.name =name; this.valuta =valuta; this.value = value;
    }



}
