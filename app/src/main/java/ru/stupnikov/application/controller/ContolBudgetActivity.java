package ru.stupnikov.application.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import ru.stupnikov.application.data.Article;
import ru.stupnikov.application.data.Fixing;
import ru.stupnikov.application.processor.Serialzer;
import ru.stupnikov.application.data.Wallet;
import ru.stupnikov.application.processor.Settings;
import ru.stupnikov.application.speculator.R;

/**
 * Created by rodion on 25.01.16.
 */

public class ContolBudgetActivity extends AppCompatActivity {

    private TextView mBalanceText;
    private EditText mEditFixing;
    private EditText mEditDate;
    private Spinner mSpinnerCategory;
    private Spinner mSpinnerSubCategory;
    private EditText mEditDescription;
    private ListView mListVievFixings;

    ArrayList<String> listCategory = new ArrayList<String>();
    ArrayList<Article> listArticles = new ArrayList<Article>();
    ArrayList<Wallet> listWallets;
    Wallet selectedWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control_budget_activity);

        mBalanceText = (TextView)findViewById(R.id.BalanceView);
        mEditFixing = (EditText)findViewById(R.id.editSum);
        mEditDate = (EditText)findViewById(R.id.editDate);
        mSpinnerCategory = (Spinner)findViewById(R.id.spinnerCategory);
        mSpinnerSubCategory = (Spinner)findViewById(R.id.spinnerSubCategory);
        mEditDescription = (EditText)findViewById(R.id.editDescription);
        mListVievFixings = (ListView)findViewById(R.id.listVievFixings);

        loadArticles();
        loadWallet();

        mSpinnerCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = mSpinnerCategory.getSelectedItem().toString();
                Article selectedArticle = Article.searhArticle(listArticles, selected);
                updateSubCategories(selectedArticle.listSubCategory);
            }
        });

    }

    public void submitButton_Click(View view) {
        addFixing();
        saveWallet();
    }

    private void addFixing (){
        Fixing fixing = new Fixing(new Date(),
               Double.valueOf( mEditFixing.getText().toString()),
                mEditDescription.getText().toString(),
                mSpinnerCategory.getSelectedItem().toString(),
                mSpinnerSubCategory.getSelectedItem().toString());
        selectedWallet.value += fixing.value;
        selectedWallet.listFixings.add(fixing);

    }

    private void saveWallet(){
        listWallets.remove(Wallet.searchWallet(listWallets, selectedWallet.name));
        listWallets.add(selectedWallet);
        Serialzer.writeWallets(getApplicationContext(), listWallets);
    }

    private void updateSubCategories(ArrayList<String> listString){
       ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listString);
        mSpinnerSubCategory.setAdapter(adapter);
    }

    private void shortMessage(String text) {
        Toast.makeText(getApplicationContext(),
                text,
                Toast.LENGTH_SHORT).show();
    }


    private void loadArticles(){
        listArticles  = (ArrayList<Article>) Serialzer.readObject(getApplicationContext(), Serialzer.FILE_ARTICLES);
        if (listArticles == null) shortMessage("Не удалось загрузить категории");
        else {
            for (Article a: listArticles){
               listCategory.add(a.category);
            }
            ArrayAdapter<String> adapterSpinnerCategory = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listCategory);
            adapterSpinnerCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinnerCategory.setAdapter(adapterSpinnerCategory);
        }
    }



    private void loadWallet(){
        listWallets = Serialzer.readWallets(getApplicationContext());
        if (listWallets == null) mBalanceText.setText("Неудачная загрузка баланса");
            else {
                selectedWallet = Wallet.searchWallet
                        (listWallets, new Settings(getApplicationContext()).loadParametrString(Settings.DEFAULT_WALLET));
                if (selectedWallet ==null) {
                    shortMessage("Кошелек по умолчание не выбран");
                }else {
                    shortMessage(selectedWallet.name);
                    mBalanceText.setText(selectedWallet.name + ": " + selectedWallet.value + " " + selectedWallet.valuta);

                }
            }
    }
}
