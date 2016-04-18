package ru.stupnikov.application.processor;

import android.content.Context;

import java.util.ArrayList;

import ru.stupnikov.application.data.Article;
import ru.stupnikov.application.data.Wallet;

/**
 * Created by rodion on 08.03.16.
 */
public class DefaultGenerator  {


    public static boolean generateAllDefaulParams(Context context){
        if (genericWallets(context) && generateArticles(context)) return  true;
        else return false;

    }

    public static boolean genericWallets(Context context){
        try {
            ArrayList<Wallet> listWallets = new ArrayList<Wallet>();
            listWallets.add(createDedaultWallet());
            Serialzer.writeWallets(context, listWallets);
            return true;
        } catch (Exception e){
            return  false;
        }
    }

    public static ArrayList<Wallet> getDefaultWallets(Context context){
        try {
            ArrayList<Wallet> listWallets = new ArrayList<Wallet>();
            listWallets.add(createDedaultWallet());
            Serialzer.writeWallets(context, listWallets);
            return listWallets;
        } catch (Exception e){
            return  null;
        }
    }



    private static Wallet createDedaultWallet (){
        Wallet defaultWallet = new Wallet("Наличные");
        defaultWallet.position = 0;
        defaultWallet.value = 0;
        defaultWallet.valuta = "RUB";
        defaultWallet.listConvertableValuta.add("USD");
        defaultWallet.listConvertableValuta.add("EUR");
        return defaultWallet;
    }


    public static boolean generateArticles (Context context){
        ArrayList<Article> listArticles = new ArrayList<>();
        listArticles.add(new Article("Доходы", generateProfitItems()));
        listArticles.add(new Article("Коммунальные", new ArrayList<String>()));
        listArticles.add(new Article("Еда", new ArrayList<String>()));
        listArticles.add(new Article("Медицина", new ArrayList<String>()));
        listArticles.add(new Article("Транспорт", new ArrayList<String>()));
        Serialzer.writeObject(context, listArticles, Serialzer.FILE_ARTICLES);
        return true;
    }

    private static ArrayList<String> generateProfitItems(){
        ArrayList<String> items = new ArrayList<String>();
        items.add("Зарплата");
        items.add("Проценты");
        items.add("Подарок");
        return items;
    }
}
