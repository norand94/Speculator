package ru.stupnikov.application.orm_classes;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by rodion on 22.04.16.
 */
@Table(name = "Categoryes")
public class Category extends Model{

    @Column(name = "Name")
    private String name;

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







}
