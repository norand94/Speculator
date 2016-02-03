package ru.stupnikov.application.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
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


    private void createAllFiles(){
        Serialzer.createAllFiles(getApplicationContext());
        Settings.saveParameter(getApplicationContext(), Settings.DEFAULT_ACTIVITY, "default");
    }

    public void clearButton_Click(View view) {
        try {

            AlertDialog.Builder builder = new AlertDialog.Builder(GeneralSettingsActivity.this);
            builder.setTitle(R.string.clear_all_saves)
                    .setMessage(R.string.Delete_all_files_preserving_and_recreate_them)
                    .setCancelable(true)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            createAllFiles();
                            shortMessage("Все файлы удалены и созданы заново");
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton(R.string.no,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            AlertDialog alert = builder.create();
            alert.show();

        } catch (IllegalStateException e){
            // Брошенный, когда действие предпринимается в тот момент, когда виртуальная машина не находится в правильном состоянии.
            e.printStackTrace();
            shortMessage("Неправильное состояние VM");
        } catch (Exception e){
            e.printStackTrace();
            shortMessage("Неизвестная ошибка");
        }
    }
}
