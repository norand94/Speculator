package ru.stupnikov.application.processor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rodion on 15.04.16.
 */
public class DBmanager extends SQLiteOpenHelper {

    public DBmanager (Context context){
        super(context, "userBD", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table wallet (" +
                "id integer primary key autoincrement, " +
                "name_wallet text," +
                "valuta text," +
                "value integer," +
                "" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
