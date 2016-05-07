package ru.stupnikov.application.data;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.Date;

import ru.stupnikov.application.orm_classes.Category;
import ru.stupnikov.application.orm_classes.Subcategory;

/**
 * Created by rodion on 25.01.16.
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
    private Wallet wallet;

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

    public Fixing(Wallet wallet, Date date, double value, String description, Category category, Subcategory subcategory) {
        this.wallet = wallet;
        this.date = date;
        this.value = value;
        this.description = description;
        this.category = category;
        this.subcategory = subcategory;
    }

    public Wallet getWallet() {
        return wallet;
    }
}
