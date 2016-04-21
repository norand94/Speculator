package ru.stupnikov.application.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.ArrayList;

import ru.stupnikov.application.processor.DBmanager;

/**
 * Created by rodion on 21.01.16.
 */
public class Wallet implements Serializable {

    public String name = "default";
    public String valuta = "RUB";
    public double value = 0;
    public int position = 0;
    public ArrayList<String> listConvertableValuta = new ArrayList<String>();
    public ArrayList<Fixing>listFixings =new ArrayList<Fixing>();

    public Wallet() {}
    public Wallet(String name) {this.name = name;}
    public Wallet(String name, String valuta, double value, int position,
                  ArrayList<String> listConvertableValuta, ArrayList<Fixing> listFixings) {
        this.name =name; this.valuta = valuta; this.value =value; this.position =position;
        this.listConvertableValuta = listConvertableValuta;
        this.listFixings = listFixings;
    }

    public Wallet(String name, String valuta, double value){
        this.name =name; this.valuta =valuta; this.value =value;
    }


  public static Wallet searchWallet(ArrayList<Wallet> listWallets ,String name){
      for (Wallet w: listWallets){
          if(w.name.equals(name)) return w;
      }
      return null;
  }

    public static ArrayList<Wallet> readWalletDB (Context context) {
        DBmanager DBhelper = new DBmanager(context);
        SQLiteDatabase db = DBhelper.getReadableDatabase();
        Cursor table = db.query(DBmanager.TABLE_WALLET, null, null, null, null, null, null);

        Wallet wallet;
        ArrayList<Wallet> listWallets = new ArrayList<Wallet>();

        if(table.moveToFirst()) {
            do {
                wallet = new Wallet(
                  table.getString(table.getColumnIndex(DBmanager.WALLET_name_wallet)),
                  table.getString(table.getColumnIndex(DBmanager.WALLET_valuta)),
                  table.getDouble(table.getColumnIndex(DBmanager.WALLET_value))
                );
                listWallets.add(wallet);
            } while (table.moveToNext());
            return listWallets;
        }
        else return null;
    }

    public void addThisWalletToDB (Context context){
        DBmanager DBhelper = new DBmanager(context);
        SQLiteDatabase db = DBhelper.getWritableDatabase();
        double deb = value;
        ContentValues cv = new ContentValues();
        cv.put(DBmanager.WALLET_wallet_id, 0);
        cv.put(DBmanager.WALLET_name_wallet, name );
        cv.put(DBmanager.WALLET_valuta, valuta);
        cv.put(DBmanager.WALLET_value, value);
        db.insert(DBmanager.TABLE_WALLET, null, cv);
    }

    public void  updateValueToDB (Context context){
        DBmanager DBhelper = new DBmanager(context);
        SQLiteDatabase db = DBhelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBmanager.WALLET_value, value);
        db.update(DBmanager.TABLE_WALLET, cv,
                DBmanager.WALLET_name_wallet +  "= ?", new String[]{name});
    }
}
