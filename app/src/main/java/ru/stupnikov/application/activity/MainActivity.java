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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.stupnikov.application.adapter.ValutaAdapter;
import ru.stupnikov.application.adapter.ValutaContainer;
import ru.stupnikov.application.data.Valuta;
import ru.stupnikov.application.data.Wallet;
import ru.stupnikov.application.processor.DefaultGenerator;
import ru.stupnikov.application.processor.Serialzer;
import ru.stupnikov.application.processor.Converter;
import ru.stupnikov.application.processor.Settings;
import ru.stupnikov.application.speculator.R;


public class MainActivity extends AppCompatActivity {

//    private boolean updated = false;

    private TextView mDateText;
    private ListView mListViewValuta;
    private ListView mListViewWallets;
    private TextView mDateText1;
    private TextView mDateText2;

    ArrayList<Valuta> listValuta;
    ArrayList<ValutaContainer> listValutaCon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preCreateMethod();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListViewValuta = (ListView)findViewById(R.id.valutaListViev);
        mDateText = (TextView)findViewById(R.id.dateTextView);
        mListViewWallets = (ListView)findViewById(R.id.walletsListView);
        mDateText1 = (TextView)findViewById(R.id.dateTextView1);
        mDateText2 = (TextView)findViewById(R.id.dateTextView2);

        mDateText.setText( new Date().toString());

        listValutaCon = new ArrayList<ValutaContainer>();
        listValutaCon.add(new ValutaContainer("RUB", R.drawable.dollar_icon, 1, 1));

        listValuta = new ArrayList<Valuta>();
        listValuta.add(new Valuta("RUB", 1));

        downloadValuta();
        loadWallets();
    }



    private void preCreateMethod(){
        String [] arrayActivity = getResources().getStringArray(R.array.array_activityes);
        String load = Settings.loadParametrString(getApplicationContext(), Settings.DEFAULT_ACTIVITY);
        if(load != null)
        if(load.equals(arrayActivity[1])){
            startActivity(new Intent(MainActivity.this, ContolBudgetActivity.class));
        }
    }

    protected void onStart(){
        super.onStart();
        loadWallets();
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
                startActivity(new Intent(MainActivity.this, GeneralSettingsActivity.class));
                return true;
            case R.id.pouchs_settings:
                startActivity(new Intent(MainActivity.this, EditWalletActivity.class));
                return true;

            case R.id.controlBudgetActivityIntent:
                startActivity(new Intent(MainActivity.this, ContolBudgetActivity.class));
                return  true;
            case R.id.editArcticleMain:
                startActivity(new Intent(MainActivity.this, EditArticleActivity.class));
                return true;

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
        startActivity(new Intent(MainActivity.this, StatisticBudgetActivity.class));
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


    private void loadWallets(){
        ArrayList<Wallet> listWallets;
        listWallets = Serialzer.readWallets(getApplicationContext());
        if (listWallets == null){
            listWallets = DefaultGenerator.getDefaultWallets(getApplicationContext());
            if(DefaultGenerator.generateAllDefaulParams(getApplicationContext()) &&
                    Serialzer.createFileFixings(getApplicationContext()))
                Toast.makeText(this,"Первый запуск. Установлены настройки по умолчанию ", Toast.LENGTH_LONG).show();
        }
        updateWalletListView(listWallets);

    }


    private void  updateValutaListView(){

        mListViewValuta.setAdapter(new ValutaAdapter(this, listValutaCon));

      /*  ArrayAdapter<String> adapterListValuta = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        for (Valuta v: listValuta){
            adapterListValuta.add(v.name + "  " + v.valueRUB);
        }
        mListViewValuta.setAdapter(adapterListValuta);*/
    }

    private  void updateWalletListView(ArrayList<Wallet> listWallets){

        ArrayAdapter<String> adapterListWallet = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        StringBuilder SB = new StringBuilder();
        if (listWallets != null) {
            for (Wallet p : listWallets) {
                SB.append("\n" + p.name + "   " + p.value + " " + p.valuta + "\n");
                for (String str : p.listConvertableValuta) {


                    double result = Converter.convertToValuta(listValuta, p.valuta, p.value, str);
                    if(result!=-1) SB.append(str + " -  " + round(result,3) + "\n");
                    else  SB.append(str + "\n");

                }
                //  SB.append("\n");
                adapterListWallet.add(SB.toString());
                SB = new StringBuilder();
            }

        } else shortMessage("\n Произошла ошибка во время чтения");

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
            Document doc = null;//Здесь хранится будет разобранный html документ
            try {
                doc = Jsoup.connect("http://www.cbr.ru/").get();

            } catch (IOException e) {
                //Если не получилось считать
                e.printStackTrace();
            }

            if (doc != null) {

                // listValutaCon = new ArrayList<ValutaContainer>();

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

        // Переработать!!!
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
