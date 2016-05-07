package ru.stupnikov.application.orm_classes;

import android.database.sqlite.SQLiteException;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodion on 22.04.16.
 */
@Table(name = "Categoryes")
public class Category extends Model{

    public static final String NAME = "Name";

    @Column(name = NAME)
    private String name;
    public List<Subcategory> listSubcategories;

    public Category(){
        super();
    }
    public Category(String name)
    {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<Category> getListCategories() {
        return new Select().from(Category.class).orderBy(NAME + " ASC").execute();
    }

    public List<Subcategory> getListSubcategories(){
        return getMany(Subcategory.class, Subcategory.CATEGORY);
    }


    public static Category findCategoryByName(String name) {
        try {
            return new Select()
                    .from(Category.class)
                    .where(NAME + "=?", name)
                    .executeSingle();
        } catch (SQLiteException e){
            e.printStackTrace();
            return null;
        }
    }









}
