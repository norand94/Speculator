package ru.stupnikov.application.processor;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rodion on 30.01.2016.
 */
public class Settings {
    public static final String FILE_GENERAL_SETTINGS = "file_general_settings";
    public static final String DEFAULT_WALLET = "default_wallet";

    private SharedPreferences.Editor editor;
    private SharedPreferences SP;

    public Settings(Context context){
        SP = context.getSharedPreferences(FILE_GENERAL_SETTINGS, context.MODE_PRIVATE);
        editor = SP.edit();
    }

    public boolean saveParameter(String parametr, String value){
        editor.putString(parametr, value);
        editor.apply();
        return true;
    }
    public String loadParametrString(String parametr){
            if (SP.contains(parametr))
            return SP.getString(parametr, "null");
            else return null;
    }

    public boolean saveParametr (String parametr, int value){
        editor.putInt(parametr, value);
        editor.apply();
        return true;
    }

    public int loadParemetrInt (String parametr){
        if (SP.contains(parametr)) return SP.getInt(parametr, -1);
        else return -1;
    }
}
