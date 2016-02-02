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
    private Spinner mSpinnerDefaultActivity;
    ArrayList<Wallet> listWallets = new ArrayList<Wallet>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_settings_activity);

        mSpinnerMainWallet = (Spinner)findViewById(R.id.spinnerMainWallet);
        mSpinnerDefaultActivity = (Spinner)findViewById(R.id.spinnerDefaultActivity);


        loadWallets();
        loadDefaultWalletSettings();

    }

/*    private void adaptSpinnerDefaultActivity(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.add(getString(R.string.main_activity));
        adapter.add(getString(R.string.budget_control_activity));
        mSpinnerDefaultActivity.setAdapter(adapter);
    }*/

    private void saveDefaultWallet(){
        try {
            if(listWallets!=null)
            Settings.saveParameter(getApplicationContext(), Settings.DEFAULT_WALLET, mSpinnerMainWallet.getSelectedItem().toString());
            else Toast.makeText(this, "Не указан кошелек", Toast.LENGTH_LONG).show();
        }
        catch (IllegalStateException e){
            e.printStackTrace();
            Toast.makeText(this, "Не указан кошелек", Toast.LENGTH_LONG).show();
        }
    }
    private void saveDefaultActivity(){
        Settings.saveParameter(getApplicationContext(), Settings.DEFAULT_ACTIVITY, mSpinnerDefaultActivity.getSelectedItem().toString());
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

    public void saveButton_Click(View view) {
        saveDefaultWallet();
        saveDefaultActivity();
        shortMessage("Сохранено");
    }


}
