package ru.stupnikov.application.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.stupnikov.application.adapter.ValutaAdapter;
import ru.stupnikov.application.adapter.ValutaContainer;
import ru.stupnikov.application.orm_classes.Valuta;
import ru.stupnikov.application.orm_classes.Wallet;
import ru.stupnikov.application.processor.Converter;
import ru.stupnikov.application.processor.FirstValuesAppGenerator;
import ru.stupnikov.application.processor.Serialzer;
import ru.stupnikov.application.processor.Settings;
import ru.stupnikov.application.speculator.R;


/**
 * Created by rodion on 08.05.16.
 */
public class GeneralActivity extends AppCompatActivity {

    private TextView mDateText;
    private ListView mListViewValuta;
    private ListView mListViewWallets;
    private TextView mDateText1;
    private TextView mDateText2;

    List<ru.stupnikov.application.orm_classes.Valuta> listValuta;
    ArrayList<ValutaContainer> listValutaCon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preCreateMethod();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInterface();
        downloadValuta();
        loadWallets();
    }

    private void initInterface() {
        mListViewValuta = (ListView)findViewById(R.id.valutaListViev);
        mDateText = (TextView)findViewById(R.id.dateTextView);
        mListViewWallets = (ListView)findViewById(R.id.walletsListView);
        mDateText1 = (TextView)findViewById(R.id.dateTextView1);
        mDateText2 = (TextView)findViewById(R.id.dateTextView2);

        mDateText.setText(new Date().toString());

        listValutaCon = new ArrayList<ValutaContainer>();
        listValutaCon.add(new ValutaContainer("RUB", R.drawable.dollar_icon, 1, 1));

        listValuta = new ArrayList<Valuta>();

        // TODO: Возможно, следует поменять, сделать загрузку из базы?
        listValuta.add(new Valuta("RUB", 1));
    }



    private void preCreateMethod(){
        ActiveAndroid.initialize(this);
        String [] arrayActivity = getResources().getStringArray(R.array.array_activityes);
        String load = Settings.loadParametrString(getApplicationContext(), Settings.DEFAULT_ACTIVITY);
        if(load != null)
            if(load.equals(arrayActivity[1])){
                startActivity(new Intent(getApplicationContext(), ContolBudgetActivity.class));
            }
    }

    protected void onStart(){
        super.onStart();
        loadWallets();
    }

    private void downloadValuta(){

        if (isNetworkConnected()) {
            shortMessage("Идет соединение...");
            AsyncParse AD = new AsyncParse();
            AD.execute();
        } else {
            shortMessage("Нет сети");
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
                startActivity(new Intent(GeneralActivity.this, GeneralSettingsActivity.class));
                return true;
            case R.id.pouchs_settings:
                startActivity(new Intent(GeneralActivity.this, EditWalletActivity.class));
                return true;

            case R.id.controlBudgetActivityIntent:
                startActivity(new Intent(GeneralActivity.this, ContolBudgetActivity.class));
                return  true;
            case R.id.editArcticleMain:
                startActivity(new Intent(GeneralActivity.this, EditArticleActivity.class));
                return true;
            case R.id.testActivityIntent:
                startActivity(new Intent(GeneralActivity.this, TestActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    private void createAllFiles(){
        Serialzer.createAllFiles(getApplicationContext());
        Settings.saveParameter(getApplicationContext(), Settings.DEFAULT_ACTIVITY, "default");
    }

    private void shortMessage(String text) {
        Toast.makeText(getApplicationContext(),
                text,
                Toast.LENGTH_SHORT).show();
    }


    private boolean isNetworkConnected() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }


    //TODO: это для тестирования новых функций
    public void dateTextView_Click(View view) {
        //startActivity(new Intent(this, MaintFragmentActivity.class));
        startActivity(new Intent(getApplicationContext(), StatisticBudgetActivity.class));
    }




    private void loadWallets(){

        List<Wallet> walletList = Wallet.getListWallets();
        if (walletList.size() == 0){
            FirstValuesAppGenerator.generateAll();
            walletList = Wallet.getListWallets();
            shortMessage("Первый запуск. Установлены настройки по умолчанию");
        }
        updateWalletListView(walletList);
    }


    private void  updateValutaListView(){
        mListViewValuta.setAdapter(new ValutaAdapter(this, listValutaCon));
    }

    private  void updateWalletListView(List<Wallet> listWallets){

        ArrayAdapter<String> adapterListWallet = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        StringBuilder SB = new StringBuilder();

        for (Wallet w : listWallets)
            if(w.valuta != null) {
                SB.append("\n" + w.name + "  " + w.value + " " + w.valuta.name + "\n");

                // TODO: не работает getListConvertableValuta
                List<Valuta> walletListValuta = w.getListConvertableValuta();
                if (walletListValuta != null)
                    for (Valuta v : walletListValuta) {
                        double result = Converter.convertToValuta(w.valuta, w.value, v);
                        SB.append(v.name + " - " + round(result, 3) + "\n");
                    }   else shortMessage("Ошибка считывания конвертируемых валют");
                adapterListWallet.add(SB.toString());
                SB = new StringBuilder();

            }
        mListViewWallets.setAdapter(adapterListWallet);
    }



    public double round(double value, int scale) {
        return Math.round(value * Math.pow(10, scale)) / Math.pow(10, scale);
    }


    class AsyncParse extends AsyncTask<Void, Void, Void> {

        // ArrayList<ValutaContainer> listValutaCon;

        String date1 = "sorry :(", date2;

        @Override
        protected Void doInBackground(Void... params) {
            Document doc = null;
            try {
                doc = Jsoup.connect("http://www.cbr.ru/").get();

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (doc != null) {

                listValutaCon.add(new ValutaContainer(
                        "USD", R.drawable.dollar_icon,
                        searchValuta(doc.select("td.weak").first(), Pattern.compile("&nbsp;(.......)")),
                        searchValuta(doc.select("div.w_data_wrap").first(), Pattern.compile("</i>(.......)"))
                ));

                listValutaCon.add(new ValutaContainer(
                        "EUR", R.drawable.dollar_icon,
                        searchValuta(doc.select("td.weak").last(), Pattern.compile("&nbsp;(.......)")),
                        searchValuta(doc.body(), Pattern.compile("</i>(.......)"), 6)
                ));

                //todo: В будущем два нижних вызова удалить, перенести данные на listValutaCon
                listValuta.add(new Valuta("USD",
                        searchValuta(doc.select("td.weak").first(), Pattern.compile("&nbsp;(.......)"))));


                listValuta.add(new Valuta("EUR",
                        searchValuta(doc.select("td.weak").last(), Pattern.compile("&nbsp;(.......)")))); // 7 символов

                date1 = searchString(doc.body().getElementsByTag("th"), Pattern.compile(">(..........)"), 0);
                date2 = searchString(doc.body().getElementsByTag("th"), Pattern.compile(">(..........)"), 1);


            }

            return null;

        }

        private double searchValuta(Element element, Pattern pattern) {
            String vtext;
            Matcher matcher = pattern.matcher(element.html());
            while (matcher.find()) {
                vtext = matcher.group(1)+"";
                return round(Double.parseDouble(vtext.replaceAll(" ", "").replace(',', '.')), 4) ; // отбрасываем знаки, мешающие преобразованию
                // округляем до 4 знаков после запятой

            }
            return -1;
        }


        private double searchValuta(Element element, Pattern pattern, int num) {
            String vtext;
            Matcher matcher = pattern.matcher(element.html());
            int i = 0;
            while (matcher.find()) {
                if (i == num) {
                    vtext = matcher.group(1)+"";
                    return Double.parseDouble(vtext.replaceAll(" ","").replace(',','.'));
                }
                i++;
            }
            return -1;
        }

        //TODO:  Переработать!!!
        private String searchString (Elements element, Pattern pattern, int num) {
            StringBuilder SB = new StringBuilder();
            Matcher matcher = pattern.matcher(element.html());
            int i = 0;
            while (matcher.find()){
                if (i==num) {
                    SB.append(matcher.group(1) + "\n"); break;
                }
                i ++;
            }

            return SB.toString();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            updateValutaListView();
            mDateText1.setText(date1);
            mDateText2.setText(date2);
        }
    }
}
