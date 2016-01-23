package ru.stupnikov.application.data;

import android.content.Context;
import android.content.SharedPreferences;

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

    public boolean writePouchs(ArrayList<Pouch> pouchs){
        try {
            FileOutputStream fOS = context.openFileOutput(FILE_POUCHS, Context.MODE_PRIVATE);
            ObjectOutputStream oOS = new ObjectOutputStream(fOS);
            oOS.writeObject(pouchs);
            oOS.close();
            return true;
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Pouch> readPouchs (){
        try {
            FileInputStream fIS = context.openFileInput(FILE_POUCHS);
            ObjectInputStream oIS = new ObjectInputStream(fIS);
            ArrayList<Pouch> pouchs = (ArrayList<Pouch>)oIS.readObject();
            oIS.close();
            return  pouchs;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e2){
            e2.printStackTrace();
            return  null;
        }
    }

}
