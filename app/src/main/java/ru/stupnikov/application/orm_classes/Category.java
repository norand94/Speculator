package ru.stupnikov.application.orm_classes;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by rodion on 22.04.16.
 */
public class Category extends SugarRecord {
    private Long id;
    private String name;

    public Category(){

    }
    public Category(String name){
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    List<Subcategory> getListSubcategory(){
        return Subcategory.find(Subcategory.class, "category = ?", String.valueOf(getId())) ;
    }


}
