package ru.stupnikov.application.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
    private Button addButton;
    private Button readButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        testTextView = (TextView)findViewById(R.id.testTextView);
        numerEditText = (EditText) findViewById(R.id.numerEditText);
        addButton = (Button)findViewById(R.id.writeButton);
        readButton = (Button) findViewById(R.id.readButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Wallet> listWallet = Serialzer.readWallets(getApplicationContext());
                Wallet wallet = listWallet.get(Integer.valueOf(numerEditText.getText().toString()));
                testTextView.append("\n " + wallet.name + "  записан");
                wallet.addThisWalletToDB(getApplicationContext());
            }
        });
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Wallet> listWallet = new ArrayList<Wallet>();
                testTextView.append("Считанные кошельки:");
                listWallet = Wallet.readWalletDB(getApplicationContext());
                for (Wallet w: listWallet){
                    testTextView.append("\n"+ w.name);
                }
            }
        });
    }

}
