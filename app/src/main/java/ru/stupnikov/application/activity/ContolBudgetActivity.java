package ru.stupnikov.application.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;
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
    private DatePicker mEditDate;
    private Spinner mSpinnerCategory;
    private Spinner mSpinnerSubCategory;
    private EditText mEditDescription;
    private ListView mListVievFixings;
    private Button mButtonSwitch;

 //   ArrayList<String> listCategory = new ArrayList<String>();
    ArrayList<Article> listArticles = new ArrayList<Article>();
    ArrayList<Wallet> listWallets;
    Wallet selectedWallet;

  /*  private  int year;
    private int month;
    private int day;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_budget);

        mBalanceText = (TextView) findViewById(R.id.BalanceView);
        mEditFixing = (EditText) findViewById(R.id.editSum);
        mEditDate = (DatePicker) findViewById(R.id.editDate);
        mSpinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
        mSpinnerSubCategory = (Spinner) findViewById(R.id.spinnerSubCategory);
        mEditDescription = (EditText) findViewById(R.id.editDescription);
        mListVievFixings = (ListView) findViewById(R.id.listVievFixings);
        mButtonSwitch = (Button) findViewById(R.id.buttonSwitch);


        Calendar today = Calendar.getInstance();


        loadArticles();
        loadWallets();
        mSpinnerSubCategory.setSelection(0);
        mSpinnerCategory.setSelection(0);
        updateListViewFixing();

        mSpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = mSpinnerCategory.getSelectedItem().toString();
                Article selectedArticle = Article.searhArticle(listArticles, selected);
                updateSubCategories(selectedArticle.listSubCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    public void submitButton_Click(View view) {
        try {
            if (!mEditFixing.getText().toString().equals("")) {
            try {

                addFixing();
            }  catch (NullPointerException e){
                e.printStackTrace();
                addFixing("");
            }
                updateBalanceView();
                saveWallet();
                updateListViewFixing();

            } else {
            Toast.makeText(this, "Необходимо ввести число!", Toast.LENGTH_SHORT).show();
            }
        } catch (NullPointerException e){
            e.printStackTrace();
            shortMessage("Не созданы кошельки и/или статьи");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.general_settings:
                startActivity(new Intent(this, GeneralSettingsActivity.class));
                return true;
            case R.id.main_activity_intent:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.pouchs_settings:
                startActivity(new Intent(this, EditWalletActivity.class));
                return true;
            case R.id.controlBudgetActivityIntent:
                startActivity(new Intent(this, ContolBudgetActivity.class));
                return  true;
            case R.id.editArcticleMain:
                startActivity(new Intent(this, EditArticleActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addFixing (String subCategory){


          /*  Fixing fixing = new Fixing(
                    new Date(mEditDate.getYear(), mEditDate.getMonth() + 1, mEditDate.getDayOfMonth()),
                    mButtonSwitch.getText().toString().equals("+"),
                    Double.valueOf(mEditFixing.getText().toString()),
                    mEditDescription.getText().toString(),
                    mSpinnerCategory.getSelectedItem().toString(),
                    subCategory);

            if (mButtonSwitch.getText().toString().equals("-"))
                selectedWallet.value -= fixing.value;
            else selectedWallet.value += fixing.value;
            selectedWallet.listFixings.add(fixing);*/
    }

    private void addFixing (){

       /* Fixing fixing = new Fixing(
                new Date(mEditDate.getYear(), mEditDate.getMonth() + 1, mEditDate.getDayOfMonth()),
                mButtonSwitch.getText().toString().equals("+"),
                Double.valueOf(mEditFixing.getText().toString()),
                mEditDescription.getText().toString(),
                mSpinnerCategory.getSelectedItem().toString(),
                mSpinnerSubCategory.getSelectedItem().toString());

        if (mButtonSwitch.getText().toString().equals("-"))
            selectedWallet.value -= fixing.value;
        else selectedWallet.value += fixing.value;
        selectedWallet.listFixings.add(fixing);*/
    }


    private void saveWallet(){
        listWallets.remove(Wallet.searchWallet(listWallets, selectedWallet.name));
        listWallets.add(selectedWallet);
        Serialzer.writeWallets(getApplicationContext(), listWallets);
    }

    private void updateBalanceView(){
        mBalanceText.setText(selectedWallet.name + ": " + selectedWallet.value + " " + selectedWallet.valuta);
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
            ArrayAdapter<String> adapterSpinnerCategory = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
            for (Article a: listArticles){
               adapterSpinnerCategory.add(a.category);
            }
            adapterSpinnerCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinnerCategory.setAdapter(adapterSpinnerCategory);
        }
    }



    private void loadWallets(){
        listWallets = Serialzer.readWallets(getApplicationContext());
        if (listWallets == null) mBalanceText.setText(getResources().getString(R.string.Not_selected_wallet));
            else {
                selectedWallet = Wallet.searchWallet
                        (listWallets, Settings.loadParametrString(getApplicationContext(), Settings.DEFAULT_WALLET));
                if (selectedWallet ==null) {
                    shortMessage("Кошелек по умолчанию не выбран. Автоустановка...");
                    selectedWallet = listWallets.get(0);
                    Settings.saveParameter(getApplicationContext(), Settings.DEFAULT_WALLET, selectedWallet.name);
                    updateBalanceView();
                }else {
                   updateBalanceView();
                }
            }
    }

    public void buttonSwith_Click(View view) {
        if (mButtonSwitch.getText().toString().equals("-")){
            mButtonSwitch.setText("+");
        } else {
            mButtonSwitch.setText("-");
        }
    }

    private void updateListViewFixing(){
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        StringBuilder SB;
        try {
            for (Fixing f : selectedWallet.listFixings) {
                SB = new StringBuilder();
                SB.append(printDate(f.date) + "   "
                        + f.category + ":" + f.subcategory + "    ->   ");
                if (f.isProfit) SB.append("+ ");
                else SB.append("- ");
                SB.append("" + f.value);
                adapter.add(SB.toString());
            }

            mListVievFixings.setAdapter(adapter);
        } catch (NullPointerException e){
            e.printStackTrace();
            adapter.add("Не удалось загрузить фиксации для кошелька");
            mListVievFixings.setAdapter(adapter);
        }*/
    }

    public static String printDate(Date date){
        return "[" + date.getDate() + "." + date.getMonth() + "." + date.getYear() +  "]";
    }

}
