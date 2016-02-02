package ru.stupnikov.application.processor;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rodion on 30.01.2016.
 */
public class Settings {
    public static final String FILE_GENERAL_SETTINGS = "file_general_settings";
    public static final String DEFAULT_WALLET = "default_wallet";
    public static final String DEFAULT_ACTIVITY = "default_activity";

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


    public static void saveParameter(Context context, String parametr, String value){
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(parametr, value);
        editor.apply();

    }

    public static String loadParametrString(Context context, String parametr){
        try {
            SharedPreferences SP = getSharedPreferences(context);
            if (SP.contains(parametr))
                return SP.getString(parametr, "null");
            else return null;
        } catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }

    public static void saveParametr (Context context, String parametr, int value){
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(parametr, value);
        editor.apply();

    }

    public static int loadParemetrInt (Context context, String parametr){
        try {
            SharedPreferences SP = getSharedPreferences(context);
            if (SP.contains(parametr)) return SP.getInt(parametr, -1);
            else return -1;
        } catch (NullPointerException e){
            e.printStackTrace();
            return -1;
        }
    }
}
