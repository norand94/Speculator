package ru.stupnikov.application.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ru.stupnikov.application.adapter.WalletAdapter;
import ru.stupnikov.application.orm_classes.Wallet;
import ru.stupnikov.application.speculator.R;

/**
 * Created by rodion on 22.01.16.
 */
public class EditWalletActivity extends AppCompatActivity {

    private EditText mEditName;
    private EditText mEditSum;
    private Spinner mSpinnerValuta;
    private Spinner mSpinnerConvertableValuta;
    private TextView mListConvertableValuta;

    private ListView mListWallets;

    private Button mAddPouchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInterface();
    }

    private void initInterface(){
        setContentView(R.layout.activity_edit_wallet);
        mEditName = (EditText)findViewById(R.id.editNamePouch);
        mEditSum = (EditText)findViewById(R.id.editValuta);
        mSpinnerValuta = (Spinner)findViewById(R.id.spinnerValuta);
        mSpinnerConvertableValuta = (Spinner)findViewById(R.id.spinnerConvertableValuta);
        mListConvertableValuta = (TextView)findViewById(R.id.textConvertableValuta);
        mListWallets = (ListView)findViewById(R.id.walletsListView);

        mSpinnerValuta.setSelection(2);
    }

    public void loadWallets(){
        List<Wallet> walletList = Wallet.getListWallets();
        mListWallets.setAdapter(new WalletAdapter(walletList, this));
    }


    public void addDeleteButton_Click(View view) {

    /*    String conValuta = mSpinnerConvertableValuta.getSelectedItem().toString();
    if(listConvertableValuta.indexOf(conValuta) != -1){
        listConvertableValuta.remove(conValuta);
        updateListConvertableValuta();
    } else {
        listConvertableValuta.add(conValuta);
        updateListConvertableValuta();
        }*/

    }

    private boolean FieldsNotEmpty(){
        if(!mEditName.getText().toString().equals("") && !mListConvertableValuta.getText().equals("") && !mEditSum.getText().toString().equals("")){
            return true;
        } else return false;
    }

    private void updateListConvertableValuta(){
  /*      mListConvertableValuta.setText("");
        for (String str : listConvertableValuta){
            mListConvertableValuta.append(str + ",  ");
        }*/
    }
    private void updateListWallets() {
   /*     mListWallets.setText("");
        for (Wallet p: listWallets){
            mListWallets.append("" + p.name + " -  " + p.value + " " + p.valuta + "\n");
            for (String str : p.listConvertableValuta){
                mListWallets.append(str + ", ");
            }
            mListWallets.append("\n");
        }*/

    }

    public void addPouchButton_Click(View view) {

       /*     listWallets.add(new Wallet(mEditName.getText().toString(),
                    mSpinnerValuta.getSelectedItem().toString(),
                    Double.valueOf(mEditSum.getText().toString()),
                    0,
                    listConvertableValuta,
                    new ArrayList<Fixing>()
            ));

            if (saveWallet()) {
                updateListWallets();

            }*/
    }

    private void shortMessage(String text){
        Toast.makeText(getApplicationContext(),
                text,
                Toast.LENGTH_SHORT).show();
    }

    private void longMessage(String text){
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
    }

    private void clearAll(){
         mEditName.setText("");
        mEditSum.setText("0");
        mListConvertableValuta.setText("");
    }


    public void deletePouchButton_Click(View view) {
    /*    if(mEditName.getText().toString().equals("")){
            mEditName.setHint("Введите здесь название кошелька, который вы желаете удалить");
            shortMessage("Введите в поле название кошелька, который вы желаете удалить");
        } else {
            Wallet selected = null;
            for (Wallet p : listWallets){
                if(p.name.equals(mEditName.getText().toString())) selected = p;
            }
            if (selected!=null) {
                listWallets.remove(selected);
                saveWallet();
                updateListWallets();
                clearAll();
            }
        }
    }*/
    }
}
