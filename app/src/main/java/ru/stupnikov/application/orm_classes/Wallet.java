package ru.stupnikov.application.orm_classes;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by rodion on 22.04.16.
 */
@Table(name = "Wallets")
public class Wallet extends Model{


    public static final String NAME = "Name";
    public static final String VALUTA = "Valuta";
    public static final String VALUE = "Value";

    @Column(name = NAME)
    public String name;

    @Column(name = VALUTA)
    public Valuta valuta;

    @Column(name = VALUE)
    public double value;

    public Wallet(){

    }

    public Wallet(String name, Valuta valuta, double value) {
        this.name = name;
        this.valuta = valuta;
        this.value = value;
    }

    public static List<Wallet> getListWallets(){
        return new Select().from(Wallet.class).orderBy(NAME + " ASC").execute();
    }

    public List<Fixing> getListFixing(){
        return getMany(Fixing.class, Fixing.WALLET );
    }


}
