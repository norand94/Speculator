package ru.stupnikov.application.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import ru.stupnikov.application.data.Wallet;
import ru.stupnikov.application.processor.Serialzer;
import ru.stupnikov.application.speculator.R;

/**
 * Created by rodion on 18.04.16.
 */
public class TestActivity extends AppCompatActivity {

    private TextView testTextView;
    private EditText numerEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        testTextView = (TextView)findViewById(R.id.testTextView);
        numerEditText = (EditText) findViewById(R.id.numerEditText);
    }

    public void addWallettoDB(View view) {
        ArrayList<Wallet> listWallet = Serialzer.readWallets(this);
        Wallet wallet = listWallet.get(Integer.valueOf(numerEditText.getText().toString()));
        testTextView.append("\n " + wallet.name + "  записан");
        wallet.addThisWalletToDB(this);
    }

    public void readDB(View view) {
        ArrayList<Wallet> listWallet = new ArrayList<Wallet>();
        testTextView.append("Считанные кошельки:");
        listWallet = Wallet.readWalletDB(this);
        for (Wallet w: listWallet){
            testTextView.append("\n"+ w.name);
        }
    }
}
