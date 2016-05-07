package ru.stupnikov.application.orm_classes;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

import ru.stupnikov.application.data.*;

/**
 * Created by rodion on 22.04.16.
 */
@Table(name ="Fixings")
public class Fixing extends Model {

    public static final String WALLET = "Wallet";
    public static final String DATE = "Date";
    public static final String VALUE = "Value";
    public static final String DESCRIPTION = "Description";
    public static final String CATEGORY = "Category";
    public static final String SUBCATEGORY = "Subcategory";

    @Column(name = WALLET)
    private ru.stupnikov.application.data.Wallet wallet;

    @Column(name = DATE)
    public Date date;

    @Column(name = VALUE)
    public double value;

    @Column(name = DESCRIPTION)
    public String description;

    @Column(name = CATEGORY)
    public Category category;

    @Column(name = SUBCATEGORY)
    public Subcategory subcategory;

    public Fixing() {
    }

    public Fixing(ru.stupnikov.application.data.Wallet wallet, Date date, double value, String description, Category category, Subcategory subcategory) {
        this.wallet = wallet;
        this.date = date;
        this.value = value;
        this.description = description;
        this.category = category;
        this.subcategory = subcategory;
    }

    public ru.stupnikov.application.data.Wallet getWallet() {
        return wallet;
    }
}



