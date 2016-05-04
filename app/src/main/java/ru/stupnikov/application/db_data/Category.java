package ru.stupnikov.application.db_data;


/**
 * Created by rodion on 22.04.16.
 */

public class Category  {

    private Long id;
    private String name;

    public Category(){

    }
    public Category(String name){
        this.name = name;
    }


    public String getName() {
        return name;
    }

    //unstable



    public Long getId() {
        return id;
    }


    public static final String TABLE_NAME = "category";
    public static final String CATEGORY_ID = "category_id";
    public static final String NAME = "name";

    public static final String CREATE_SCRIPT =
            "CREATE TABLE " + TABLE_NAME + "  (" +
                    " " + CATEGORY_ID   + " integer primary key autoincrement, " +
                    " " + NAME + " text NOT NULL" +
                    ");";
}
