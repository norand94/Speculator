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
    public static final String ID_CATEGORY = "id_category";

    @Column(name = NAME)
    private String name;

    @Column(name = ID_CATEGORY)
    private Long id_category; //Вторичный ключ


    public Subcategory(){

    }
    public Subcategory(String name, Long id_category){
        this.name = name; this.id_category = id_category;
    }


    public String getName() {
        return name;
    }

    public Long getId_category() {
        return id_category;
    }


    public static List<Subcategory> getListSubcategory(Category category){
        try {


            return new Select().from(Subcategory.class)
                    .where(ID_CATEGORY + " = ? ", category.getId())
                    .orderBy(NAME + " ASC ")
                    .execute();
        }catch (SQLiteException e){
            e.printStackTrace();
            return null;
        }
    }

}
