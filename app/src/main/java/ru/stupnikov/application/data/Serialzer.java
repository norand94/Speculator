package ru.stupnikov.application.data;

import android.content.Context;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by rodion on 21.01.16.
 */

public class Serialzer  {

    private Context context;
    public final static String FILE_POUCHS = "file_pouchs.jdat";

    public Serialzer(Context context) {
        this.context = context;
    }

    public boolean createFile(){
        try {


            FileOutputStream fOS = context.openFileOutput(FILE_POUCHS, context.MODE_WORLD_READABLE);
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

    public boolean writePouchs(ArrayList<Wallet> wallets){
        try {
            FileOutputStream fOS = context.openFileOutput(FILE_POUCHS, context.MODE_PRIVATE);

            ObjectOutputStream oOS = new ObjectOutputStream(fOS);
            oOS.writeObject(wallets);
            oOS.close();
            return true;
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Wallet> readPouchs (){
        try {
            FileInputStream fIS = context.openFileInput(FILE_POUCHS);
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
