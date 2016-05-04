package ru.stupnikov.application.db_data;


/**
 * Created by rodion on 22.04.16.
 */

public class Subcategory  {
    private Long id;
    private String name;
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

}
