package ru.stupnikov.application.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rodion on 27.01.16.
 */
public class Article implements Serializable {
    public String category;
    public ArrayList<String> listSubCategory;

    public Article(String category, ArrayList<String> listSubCategory ){
        this.category = category;
        this.listSubCategory = listSubCategory;
    }
}
