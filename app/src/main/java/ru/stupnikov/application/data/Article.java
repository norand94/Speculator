package ru.stupnikov.application.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rodion on 27.01.16.
 */
public class Article implements Serializable {
    public String category;
    public ArrayList<String> listSubCategory;
 //   public boolean isProfit;

    public Article(String category, ArrayList<String> listSubCategory
                   //,boolean isProfit
    ){
        this.category = category;
        this.listSubCategory = listSubCategory;
      //  this.isProfit = isProfit;
    }

    public  boolean searh_subcategory(String subcategory){
        for (String sub : listSubCategory){
            if (sub.equals(subcategory))return  true;
        }
        return false;
    }

    public static Article searhArticle(ArrayList<Article> listArticles, String category){
        for (Article a: listArticles){
            if (a.category.equals(category)) return a;
        }
        return null;
    }

}
