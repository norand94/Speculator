package ru.stupnikov.application.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

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


     /*
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.valuta, R.layout.edit_pouch_activity);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
*/
    }


    public void add_rm_button_Click(View view) {
       ;
        Toast.makeText(getApplicationContext(),
                mSpinner.getSelectedItem().toString(),
                Toast.LENGTH_SHORT).show();


    }
}
