package ru.stupnikov.application.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ru.stupnikov.application.data.Wallet;
import ru.stupnikov.application.orm_classes.Category;
import ru.stupnikov.application.orm_classes.Subcategory;
import ru.stupnikov.application.processor.DBmanager;
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
    private Button sqlrequestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        testTextView = (TextView)findViewById(R.id.testTextView);
        numerEditText = (EditText) findViewById(R.id.numerEditText);
        addButton = (Button)findViewById(R.id.writeButton);
        readButton = (Button) findViewById(R.id.readButton);
        sqlrequestButton = (Button) findViewById(R.id.sqlrequestButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //writeWalletOfIndex();
               // writeCategory();
                WriteBD WBD = new WriteBD();
                WBD.execute();
            }
        });

       /* readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Wallet> listWallet = new ArrayList<Wallet>();
                testTextView.append("\n\nСчитанные кошельки:");
                listWallet = Wallet.readWalletDB(getApplicationContext());
                for (Wallet w: listWallet){
                    testTextView.append("\n\n" +
                            w.name + "\n" +
                            w.valuta + "\n" +
                            w.value
                    );
                }
            }
        });

        sqlrequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBmanager dBmanager = new DBmanager(getApplicationContext());
                SQLiteDatabase db = dBmanager.getWritableDatabase();
                db.execSQL(numerEditText.getText().toString());
            }
        });*/
    }

    private void writeWalletOfIndex(){
        ArrayList<Wallet> listWallet = Serialzer.readWallets(getApplicationContext());
        Wallet wallet = listWallet.get(Integer.valueOf(numerEditText.getText().toString()));
        testTextView.append("\n Записано: \n " + wallet.name);
        wallet.addThisWalletToDB(getApplicationContext());
    }

    private void writeCategory(){
        Category category = new Category(numerEditText.getText().toString());
        category.save();
        Toast.makeText(getApplicationContext(), "Записано!", Toast.LENGTH_SHORT).show();
    }

    class WriteBD extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Subcategory subcategory = new Subcategory("Субпобеда" , (long)3);

            // Category category = new Category("Победа");
            //category
            //category.save();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "Записано!", Toast.LENGTH_SHORT).show();
        }
    }

}
