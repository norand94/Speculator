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

    public final static String FILE_WALLETS = "file_wallets";
    public final static String FILE_ARTICLES = "file_articles";
    public final static String FILE_FIXINGS = "file_fixings";


    public static boolean createFileWallets(Context context){
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

    public static boolean createFileArticles(Context context){
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

    //TODO: Это зачем, если Wallet уже содержит коллекцию Fixing ??
    public static boolean createFileFixings (Context context){
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

    public static boolean createAllFiles (Context context){
        if (
                createFileArticles(context) &&
                createFileFixings(context) &&
                createFileWallets(context)
                ) {
            return true;
        }
        return false;
    }


/*    public static boolean allSavesIsExist (Context context){
        if (readWallets(context) != null && readObject(context, FILE_ARTICLES) != null &&
               readObject(context, FILE_FIXINGS) != null  ) return true;
        else return false;
    }
    */

    public static boolean writeObject(Context context ,Object object, String file){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(file, context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
            return  true;
        } catch (IOException e){
            e.printStackTrace();
            return false;
        } catch (NullPointerException e){
            e.printStackTrace();
            createAllFiles(context);
            return false;
        }
    }

    public static Object readObject(Context context, String file){
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
        } catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }


    public static boolean writeWallets(Context context, ArrayList<Wallet> wallets){
        try {
            FileOutputStream fOS = context.openFileOutput(FILE_WALLETS, context.MODE_PRIVATE);

            ObjectOutputStream oOS = new ObjectOutputStream(fOS);
            oOS.writeObject(wallets);
            oOS.close();
            return true;
        } catch (IOException e){
            e.printStackTrace();
            return false;
        } catch (NullPointerException e){
            e.printStackTrace();
            createFileWallets(context);
            return false;
        }
    }

    public static ArrayList<Wallet> readWallets(Context context){
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
        } catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }

}
