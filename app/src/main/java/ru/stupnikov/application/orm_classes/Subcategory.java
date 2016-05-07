package ru.stupnikov.application.orm_classes;

import android.database.sqlite.SQLiteException;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by rodion on 22.04.16.
 */

@Table(name = "Subcategories")
public class Subcategory extends Model {
    public static final String NAME = "Name";
    public static final String CATEGORY= "Category";

    @Column(name = NAME)
    private String name;


    @Column(name =CATEGORY)
    private Category category;


    public Subcategory(){

    }

    public Subcategory(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }


    public Category getCategory() {
        return category;
    }
}
