package ru.stupnikov.application.controller;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
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

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.stupnikov.application.data.Valuta;
import ru.stupnikov.application.data.Wallet;
import ru.stupnikov.application.processor.Serialzer;
import ru.stupnikov.application.processor.Converter;
import ru.stupnikov.application.speculator.R;


public class MainActivity extends AppCompatActivity {

    private boolean updated = false;
    private TextView mDateText;
    private ListView mListViewValuta;
    private ListView mListViewWallets;
   // private MotionEvent motionEvent;
    ArrayList<Valuta> listValuta;
    ArrayList<String> listTextValuta = new ArrayList<String>();
    ArrayList<String> listTextWallet = new ArrayList<String>();


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListViewValuta = (ListView)findViewById(R.id.valutaListViev);
        mDateText = (TextView)findViewById(R.id.dateTextView);
        mListViewWallets = (ListView)findViewById(R.id.walletsListView);

        mDateText.setText( new Date().toString());
        listValuta = new ArrayList<Valuta>();
        listValuta.add(new Valuta("RUB",1));

        downloadValuta();
        loadWallets();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
                shortMessage("Выбран пунк общие настройки");
                return true;
            case R.id.pouchs_settings:
                // mValutaView.append("\n Выбран пункт \"Настройки кошельков\"");
                Intent intent = new Intent(MainActivity.this, EditPouchActivity.class);
                startActivity(intent);
                return true;

            case R.id.repair_saving:
               if (new Serialzer(getApplicationContext()).createAllFiles()){
                   shortMessage("все файлы очищены и создано заново");
               } else {
                   shortMessage("неудачное очищение");
               }
                return true;
            case R.id.controlBudgetActivityIntent:
                Intent intentBudget = new Intent(MainActivity.this, ContolBudgetActivity.class);
                startActivity(intentBudget);
                return  true;
            case R.id.editArcticleMain:
                Intent intentArticle = new Intent(MainActivity.this, EditArticleActivity.class);
                startActivity(intentArticle);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


    private void shortMessage(String text) {
        Toast.makeText(getApplicationContext(),
                text,
                Toast.LENGTH_SHORT).show();
    }

    public boolean isInternetAvailable() {
        try {

            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name

            if (ipAddr.equals("")) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            return false;
        }

    }


    private boolean isNetworkConnected() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }


    public void dateTextView_Click(View view) {
        downloadValuta();

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
        AsyncSerialize AS = new AsyncSerialize();
        AS.execute();
    }

    private void  updateValutaListView(){
        mListViewValuta.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listTextValuta));
    }

    private  void updateWalletListView(){
       // String [] test = new String[]{"1", "2", "3"};
        mListViewWallets.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listTextWallet));
    }






    //Google API (???)
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://ru.stupnikov.application.speculator/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://ru.stupnikov.application.speculator/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public double round(double value, int scale) {
        return Math.round(value * Math.pow(10, scale)) / Math.pow(10, scale);
    }

    class AsyncSerialize extends AsyncTask<Void, Void, Void> {

        public ArrayList<Wallet> listWallets = null;

        @Override
        protected Void doInBackground(Void... params) {

            listWallets = new Serialzer(getApplicationContext()).readWallets();
            if (listWallets == null){
                listWallets = new ArrayList<Wallet>();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            StringBuilder SB = new StringBuilder();
            if (listWallets != null) {
                for (Wallet p : listWallets) {
                    SB.append("\n" + p.name + "   " + p.value + " " + p.valuta + "\n");
                    for (String str : p.listConvertibleValuta) {


                        double result = new Converter(listValuta).convertToValuta(p.valuta, p.value, str);
                        if(result!=-1) SB.append(str + " -  " + round(result,3) + "\n");
                        else  SB.append(str + "\n");

                    }
                  //  SB.append("\n");
                    listTextWallet.add(SB.toString());
                    SB = new StringBuilder();
                }

            } else shortMessage("\n Произошла ошибка во время чтения");
            updateWalletListView();
        }
    }

    class AsyncParse extends AsyncTask<Void, Void, Void> {


        StringBuilder SB = new StringBuilder();

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


                listValuta.add(new Valuta("USD",
                        searchValuta(doc.select("td.weak").first(), Pattern.compile("&nbsp;(.......)"), 1)));


                listValuta.add(new Valuta("EUR",
                        searchValuta(doc.select("td.weak").last(), Pattern.compile("&nbsp;(.......)"), 1))); // 7 символов


                searchValuta(doc.select("div.w_data_wrap").first(), Pattern.compile("</i>(.......)"), 1);
                //searchValuta(doc.select("div.w_data_wrap").first(), Pattern.compile("</i>(.......)"), 1, "Доллар США завтра: ");

                searchValuta(doc.body(), Pattern.compile("</i>(.......)"), 1, 6);
                updated = true;

            }

            return null;

        }

        private double searchValuta(Element element, Pattern pattern, int group) {
            String vtext;
            Matcher matcher = pattern.matcher(element.html());
            while (matcher.find()) {
                vtext = matcher.group(group)+"";
                return round(Double.parseDouble(vtext.replaceAll(" ", "").replace(',', '.')), 4) ; // отбрасываем знаки, мешающие преобразованию
                         // округляем до 4 знаков после запятой

            }
            return -1;
        }


        private double searchValuta(Element element, Pattern pattern, int group, int num) {
            String vtext;
            Matcher matcher = pattern.matcher(element.html());
            int i = 0;
            while (matcher.find()) {
                //  SB.append(matcher.group());
                if (i == num) {
                    vtext = matcher.group(group)+"";
                    return Double.parseDouble(vtext.replaceAll(" ","").replace(',','.'));
                }
                i++;
            }
            return -1;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            for (Valuta v: listValuta){
                listTextValuta.add(v.name + "  " + v.valueRUB);
            }
            updateValutaListView();
        }
    }
}
