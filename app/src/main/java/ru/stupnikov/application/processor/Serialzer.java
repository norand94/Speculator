package ru.stupnikov.application.processor;

import android.content.Context;

import java.io.*;
import java.util.ArrayList;

import ru.stupnikov.application.data.Article;
import ru.stupnikov.application.data.Fixing;
import ru.stupnikov.application.data.Wallet;

/**
 * Created by rodion on 21.01.16.
 */

public class Serialzer  {

    private Context context;
    public final static String FILE_WALLETS = "file_wallets";
    public final static String FILE_ARTICLES = "file_articles";
    public final static String FILE_FIXINGS = "file_fixings";

    public Serialzer(Context context) {
        this.context = context;
    }

    public boolean createFileWallets(){
        try {


            FileOutputStream fOS = context.openFileOutput(FILE_WALLETS, context.MODE_WORLD_READABLE);
            ObjectOutputStream oOS = new ObjectOutputStream(fOS);
            oOS.writeObject(new ArrayList<Wallet>());
            oOS.flush();
            oOS.close();

            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }

    }

    public boolean createFileArticles(){
        try {
            FileOutputStream fOS = context.openFileOutput(FILE_ARTICLES, context.MODE_WORLD_READABLE);
            ObjectOutputStream oOS = new ObjectOutputStream(fOS);
            oOS.writeObject(new ArrayList<Article>());
            oOS.flush();
            oOS.close();
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return  false;
        }

    }

    public boolean createFileFixings (){
        try {
            FileOutputStream fOS = context.openFileOutput(FILE_FIXINGS, context.MODE_WORLD_READABLE);
            ObjectOutputStream oOS = new ObjectOutputStream(fOS);
            oOS.writeObject(new ArrayList<Fixing>());
            oOS.flush();
            oOS.close();
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean createAllFiles (){
        if (
                createFileArticles() &&
                createFileFixings() &&
                createFileWallets()
                ) {
            return true;
        }
        return false;
    }

    public boolean writeObject(Object object, String file){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(file, context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
            return  true;
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public Object readObject(String file){
        try {
            FileInputStream fileInputStream = context.openFileInput(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object object = (Object) objectInputStream.readObject();
            objectInputStream.close();
            return object;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }


    public boolean writeWallets(ArrayList<Wallet> wallets){
        try {
            FileOutputStream fOS = context.openFileOutput(FILE_WALLETS, context.MODE_PRIVATE);

            ObjectOutputStream oOS = new ObjectOutputStream(fOS);
            oOS.writeObject(wallets);
            oOS.close();
            return true;
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Wallet> readWallets(){
        try {
            FileInputStream fIS = context.openFileInput(FILE_WALLETS);
            ObjectInputStream oIS = new ObjectInputStream(fIS);
            ArrayList<Wallet> wallets = (ArrayList<Wallet>)oIS.readObject();
            oIS.close();
            return wallets;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e2){
            e2.printStackTrace();
            return  null;
        }
    }

}
