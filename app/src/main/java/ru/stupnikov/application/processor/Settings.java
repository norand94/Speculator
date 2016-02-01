package ru.stupnikov.application.processor;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rodion on 30.01.2016.
 */
public class Settings {
    public static final String FILE_GENERAL_SETTINGS = "file_general_settings";
    public static final String DEFAULT_WALLET = "default_wallet";

/*    private SharedPreferences.Editor editor;
    private SharedPreferences SP;*/

/*    public Settings(Context context){
        SP = context.getSharedPreferences(FILE_GENERAL_SETTINGS, context.MODE_PRIVATE);
        editor = SP.edit();
    }*/

    private static SharedPreferences.Editor getEditor(Context context){
        SharedPreferences SP = context.getSharedPreferences(FILE_GENERAL_SETTINGS, context.MODE_PRIVATE);
        return  SP.edit();
    }
    private static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(FILE_GENERAL_SETTINGS, context.MODE_PRIVATE);
    }


    public static boolean saveParameter(Context context, String parametr, String value){
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(parametr, value);
        editor.apply();
        return true;
    }
    public static String loadParametrString(Context context, String parametr){
        SharedPreferences SP =  getSharedPreferences(context);
            if (SP.contains(parametr))
            return SP.getString(parametr, "null");
            else return null;
    }

    public static boolean saveParametr (Context context, String parametr, int value){
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(parametr, value);
        editor.apply();
        return true;
    }

    public static int loadParemetrInt (Context context, String parametr){
        SharedPreferences SP = getSharedPreferences(context);
        if (SP.contains(parametr)) return SP.getInt(parametr, -1);
        else return -1;
    }
}
