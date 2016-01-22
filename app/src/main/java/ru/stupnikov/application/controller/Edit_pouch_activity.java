package ru.stupnikov.application.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;

import ru.stupnikov.application.speculator.R;

/**
 * Created by rodion on 22.01.16.
 */
public class Edit_pouch_activity extends AppCompatActivity {

    Spinner mSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_pouch_activity);
        mSpinner = (Spinner)findViewById(R.id.spinner);
        String [] arrayValuta = new String[] {"EUR", "USD", "RUB", "UAH"};
        String text  = getString(R.string.app_name);

    }

}
