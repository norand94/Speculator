package ru.stupnikov.application.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import ru.stupnikov.application.data.Wallet;
import ru.stupnikov.application.processor.Serialzer;
import ru.stupnikov.application.processor.Settings;
import ru.stupnikov.application.speculator.R;

/**
 * Created by rodion on 01.02.16.
 */
public class GeneralSettingsActivity extends AppCompatActivity {

    private Spinner mSpinnerMainWallet;

    ArrayList<Wallet> listWallets = new ArrayList<Wallet>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_settings_activity);

        mSpinnerMainWallet = (Spinner)findViewById(R.id.spinnerMainWallet);
        loadWallets();
        loadDefaultWalletSettings() ;

        mSpinnerMainWallet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               Settings.saveParameter(getApplicationContext(), Settings.DEFAULT_WALLET, mSpinnerMainWallet.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void loadDefaultWalletSettings() {
        String defaultWallet = Settings.loadParametrString(getApplicationContext(), Settings.DEFAULT_WALLET);
        if (defaultWallet != null){

        }

    }

    private void loadWallets(){
        listWallets = Serialzer.readWallets(getApplicationContext());
        if( listWallets == null){
           shortMessage("Не найдены записи о кошельках ");
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
            //ArrayList<String> listTextWallets = new ArrayList<String>();
            for (Wallet w : listWallets) {
                adapter.add(w.name);
            }
            mSpinnerMainWallet.setAdapter(adapter);
        }
    }

    private void shortMessage(String text) {
        Toast.makeText(getApplicationContext(),
                text,
                Toast.LENGTH_SHORT).show();
    }
}
