package ru.stupnikov.application.orm_classes;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by rodion on 07.05.16.
 */
@Table(name ="LinkWalletAndWaluta")
public class LinkWalletAndValuta extends Model {
    public static final String WALLET = "Wallet";
    public static final String VALUTA = "Valuta";

    @Column(name = WALLET)
    public Wallet wallet;

    @Column(name = VALUTA)
    public Valuta valuta;

    public LinkWalletAndValuta(Wallet wallet, Valuta valuta) {
        this.wallet = wallet;
        this.valuta = valuta;
    }


    public Wallet getWallet() {
        return wallet;
    }

    public Valuta getValuta() {
        return valuta;
    }
}


