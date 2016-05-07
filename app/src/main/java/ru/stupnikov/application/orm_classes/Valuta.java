package ru.stupnikov.application.orm_classes;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by rodion on 22.04.16.
 */
@Table(name = "Valuta")
public class Valuta extends Model {

    public static final String NAME = "Name";
    public static final String VALUE_RUB = "Value_RUB";
    public static final String WALLET= "Wallet";

    @Column(name = NAME)
    public String name;

    @Column(name = VALUE_RUB)
    public double valueRUB; // если это рубль, то равно =1


    public Valuta() {
    }


    public Valuta(String name, double valueRUB) {
        this.name = name;
        this.valueRUB = valueRUB;
    }
}
