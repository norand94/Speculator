package ru.stupnikov.application.processor;

import android.content.Context;

import ru.stupnikov.application.orm_classes.Category;
import ru.stupnikov.application.orm_classes.LinkWalletAndValuta;
import ru.stupnikov.application.orm_classes.Subcategory;
import ru.stupnikov.application.orm_classes.Valuta;
import ru.stupnikov.application.orm_classes.Wallet;

/**
 * Created by rodion on 07.05.16.
 */
public class FirstValuesAppGenerator {

    private static Valuta RUB = new Valuta("RUB", 1);
    private static Valuta EUR = new Valuta("EUR", -1);
    private static Valuta USD = new Valuta("USD", -1);



    private static void generateValuta(){
        RUB.save();
        EUR.save();
        USD.save();
    }

    public static void generateAll(){
        generateArticles();
        generateDedaultWallet();
    }

    private static void generateDedaultWallet (){
        generateValuta();
        Wallet defaultWallet = new Wallet("Наличные", RUB, 0);
        defaultWallet.value = 0;

        new LinkWalletAndValuta(defaultWallet, EUR).save();
        new LinkWalletAndValuta(defaultWallet, USD).save();

        defaultWallet.save();
    }


    private static void generateArticles (){

        Category Profit = new Category("Доходы");
        new Subcategory("Зарплата", Profit).save();
        new Subcategory("Проценты", Profit).save();
        new Subcategory("Подарок", Profit).save();

        new Category("Коммунальные").save();
        new Category("Еда").save();
        new Category("Медицина").save();
        new Category("Транспорт").save();
        new Category("Разное").save();


    }

}
