package ru.stupnikov.application.orm_classes;

import com.orm.dsl.Table;

import java.util.List;

/**
 * Created by rodion on 22.04.16.
 */
@Table
public class Wallet {
    private Long id;

    public String name = "default";
    public String valuta = "RUB";
    public double value = 0;

    public Wallet(){

    }
    public Wallet(String name, String valuta, double value){
        this.name =name; this.valuta =valuta; this.value = value;
    }

    public Long getId(){
        return id;
    }
    public List<Fixing> getListFixing(){
        return Fixing.find(Fixing.class, "id_wallet = ?", String.valueOf(id));
    }


}
