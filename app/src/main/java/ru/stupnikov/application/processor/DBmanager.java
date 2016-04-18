package ru.stupnikov.application.processor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by rodion on 15.04.16.
 */
public class DBmanager extends SQLiteOpenHelper  implements BaseColumns{

    public static final String DBname = "userBD";
    public static final int DBversion = 1;

    public static final String TABLE_WALLET = "wallet";
    public static final String WALLET_wallet_id = "wallet_id";
    public static final String WALLET_name_wallet = "name_wallet";
    public static final String WALLET_valuta = "valuta";
    public static final String WALLET_value = "value";

    public static final String TABLE_CATEGORY = "category";


    public DBmanager (Context context){
        super(context, DBname, null, DBversion);
    }

    public void executeSQL (String commandSQL) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(commandSQL);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_WALLET + " ( " +
                 WALLET_wallet_id + " integer primary key autoincrement, " +
                 WALLET_name_wallet + " text NOT NULL, " +
                 WALLET_valuta + " varchar(3) NOT NULL, " +
                 WALLET_value +" integer, " +
                "" + ");");
        db.execSQL("CREATE TABLE " + TABLE_CATEGORY + "  (" +
                "category_id integer primary key autoincrement, " +
                "name text NOT NULL" +
                ");");
        db.execSQL("CREATE TABLE subcategory (" +
                "subcategory integer primary key autoincrement," +
                "name text NOT NULL," +
                "category_id integer NOT NULL," +
                "FOREIGN KEY (category_id)  REFERENCES category(category_id)," +
                ");");
        db.execSQL("CREATE TABLE fixing (" +
                "fixing_id INT AUTO_INCREMENT PRIMARY KEY," +
                "date DATE NOT NULL," +
                "value DECIMAL(11,5) NOT NULL," +
                "category_id INT NOT NULL, " +
                "subcategory_id INT," +
                "wallet_id INT NOT NULL, " +
                "description VARCHAR(60), " +
                "FOREIGN KEY (category_id) REFERENCES category(category_id), " +
                "FOREIGN KEY (subcategory_id) REFERENCES subcategory(subcategory), " +
                "FOREIGN KEY (wallet_id) REFERENCES wallet(wallet_id)" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}