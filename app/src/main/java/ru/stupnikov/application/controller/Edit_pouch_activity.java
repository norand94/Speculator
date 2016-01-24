package ru.stupnikov.application.controller;

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

import java.util.ArrayList;

import ru.stupnikov.application.data.Pouch;
import ru.stupnikov.application.data.Serialzer;
import ru.stupnikov.application.speculator.R;

/**
 * Created by rodion on 22.01.16.
 */
public class Edit_pouch_activity extends AppCompatActivity {

   private   EditText mEditName;
   private EditText mEditSum;
   private Spinner mSpinnerValuta;
   private Spinner mSpinnerConvertableValuta;
    private TextView mListConvertableValuta;

    private TextView mListPouchs;
   // private ListView mListPouchs;

    private Button mAddPouchButton;

    ArrayList<Pouch> listPouchs;
    ArrayList<String>listConvertableValuta = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_pouch_activity);
        mEditName = (EditText)findViewById(R.id.editNamePouch);
        mEditSum = (EditText)findViewById(R.id.editValuta);
        mSpinnerValuta = (Spinner)findViewById(R.id.spinnerValuta);
        mSpinnerConvertableValuta = (Spinner)findViewById(R.id.spinnerConvertableValuta);
        mListConvertableValuta = (TextView)findViewById(R.id.textConvertableValuta);
        mListPouchs = (TextView)findViewById(R.id.textPouchs);
       // mListPouchs = (ListView)findViewById(R.id.listPouchs);
        listPouchs = new ArrayList<Pouch>();

        if(loadPouchs()) updateListPouchs();


     /*
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.valuta, R.layout.edit_pouch_activity);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
*/
    }



    public void addDeleteButton_Click(View view) {
    String conValuta = mSpinnerConvertableValuta.getSelectedItem().toString();
    if(listConvertableValuta.indexOf(conValuta) != -1){
        listConvertableValuta.remove(conValuta);
        updateListConvertableValuta();
    } else {
        listConvertableValuta.add(conValuta);
        updateListConvertableValuta();
    }

    }

    private boolean FieldsNotEmpty(){
        if(!mEditName.getText().toString().equals("") && !mListConvertableValuta.getText().equals("") && !mEditSum.getText().toString().equals("")){
            return true;
        } else return false;
    }

    private void updateListConvertableValuta(){
        mListConvertableValuta.setText("");
        for (String str : listConvertableValuta){
            mListConvertableValuta.append(str + ",  ");
        }
    }
    private void  updateListPouchs(){

        mListPouchs.setText("");
        for (Pouch p: listPouchs){
            mListPouchs.append(""+p.name + " -  " + p.value + " " + p.valuta +"\n");
            for (String str : p.listConvertibleValuta){
                mListPouchs.append(str + ", ");
            }
            mListPouchs.append("\n");
        }

   /*     ArrayList<String> listPouchsNames = new ArrayList<String>();
        for (Pouch p: listPouchs){
            listPouchsNames.add(p.name);

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 0, listPouchsNames);
        mListPouchs.setAdapter(adapter);*/

    }
    public void addPouchButton_Click(View view) {


       // if(FieldsNotEmpty()) {
            listPouchs.add(new Pouch(mEditName.getText().toString(),
                    mSpinnerValuta.getSelectedItem().toString(),
                    Integer.valueOf(mEditSum.getText().toString()),
                    0,
                    listConvertableValuta
            ));
            if (savePouchs()) {
                updateListPouchs();

            }
      //  } else  longMessage("Поля обязательно должны быть заполнены для добавления кошелька!");

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

    private boolean loadPouchs(){
        Serialzer serialzer = new Serialzer(getApplicationContext());
        listPouchs = serialzer.readPouchs();
        if(listPouchs ==null){
            shortMessage("При чтении произошла ошибка");
            return false;
        } else {
            shortMessage("Успешно считано");
            return true;
        }
    }

    private boolean savePouchs(){
        Serialzer serialzer = new Serialzer(getApplicationContext());
        if(serialzer.writePouchs(listPouchs)){
            shortMessage("Успешно записно");
            return true;
        } else {
            shortMessage("При записи произошла ошибка");
            return false;
        }
    }

    public void deletePouchButton_Click(View view) {
        if(mEditName.getText().toString().equals("")){
            mEditName.setHint("Введите здесь название кошелька, который вы желаете удалить");
            shortMessage("Введите в поле название кошелька, который вы желаете удалить");
        } else {
            Pouch selected = null;
            for (Pouch p : listPouchs){
                if(p.name.equals(mEditName.getText().toString())) selected = p;
            }
            if (selected!=null) {
                listPouchs.remove(selected);
                savePouchs();
                updateListPouchs();
                clearAll();
            }
        }
    }
}
