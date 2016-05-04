package ru.stupnikov.application.orm_classes;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;


import java.util.List;

/**
 * Created by rodion on 22.04.16.
 */

public class Category extends SugarRecord {

    @Unique
    private String name;

    public Category(){

    }
    public Category(String name){
        this.name = name;
    }


    @Override
    public Long getId() {
        return super.getId();
    }

    public String getName() {
        return name;
    }

    //unstable
    public  List<Subcategory> getListSubcategory(){
        return Subcategory.find(Subcategory.class, "category = ?", String.valueOf(getId())) ;
    }

    public static final String CREATE_SCRIPT =
            " ";



}
