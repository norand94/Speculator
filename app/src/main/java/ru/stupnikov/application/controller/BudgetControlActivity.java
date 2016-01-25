package ru.stupnikov.application.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import ru.stupnikov.application.processor.Serialzer;
import ru.stupnikov.application.data.Wallet;
import ru.stupnikov.application.speculator.R;

/**
 * Created by rodion on 25.01.16.
 */
public class BudgetControlActivity extends AppCompatActivity {

    private TextView mBalanceText;
    private EditText mEditFixing;
    private EditText mEditDate;
    private Spinner mSpinnerCategory;
    private Spinner mSpinnerSubCategory;
    private EditText mEditDescription;
    // private Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budget_control_activity);

        mBalanceText = (TextView)findViewById(R.id.BalanceView);
        mEditFixing = (EditText)findViewById(R.id.editFixing);
        mEditDate = (EditText)findViewById(R.id.editDate);
        mSpinnerCategory = (Spinner)findViewById(R.id.spinnerCategory);
        mSpinnerSubCategory = (Spinner)findViewById(R.id.spinnerSubCategory);
        mEditDescription = (EditText)findViewById(R.id.editDescription);
       // mSubmitButton = (Button)findViewById(R.id.submitButton);


    }

    public void submitButton_Click(View view) {

    }

    private void loadBalance(){
        ArrayList<Wallet> listWallets = new Serialzer(getApplicationContext()).readWallets();
        if (listWallets == null) mBalanceText.setText("Неудачная загрузка баланса");
            else {

            }
    }
}
