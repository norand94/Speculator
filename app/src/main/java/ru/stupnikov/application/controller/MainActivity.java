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
import android.widget.Button;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.stupnikov.application.data.Valuta;
import ru.stupnikov.application.data.Wallet;
import ru.stupnikov.application.processor.Serialzer;
import ru.stupnikov.application.processor.Converter;
import ru.stupnikov.application.speculator.R;


public class MainActivity extends AppCompatActivity {

    private boolean updated = false;
    private TextView mValutaView;
   // private MotionEvent motionEvent;
    private TextView mPouchsView;
    ArrayList<Valuta> listValuta;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mValutaView = (TextView) findViewById(R.id.textValuta);
        mPouchsView = (TextView)findViewById(R.id.textPouchs);
        listValuta = new ArrayList<Valuta>();
        listValuta.add(new Valuta("RUB",1));

        downloadValuta();
        loadPouchs();

        final Button mSaveButton = (Button) findViewById(R.id.saveButton);


        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shortMessage("Создание новых кошельков теперь проводится в другом месте");

            }
        });


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
                mValutaView.append("\n Выбран пункт \"Общие настройки\"");
                return true;
            case R.id.pouchs_settings:
                // mValutaView.append("\n Выбран пункт \"Настройки кошельков\"");
                Intent intent = new Intent(MainActivity.this, EditPouchActivity.class);
                startActivity(intent);
                return true;

            case R.id.repair_saving:
                Serialzer serialzer = new Serialzer(getApplicationContext());
                if (serialzer.createFile()) {
                    shortMessage("Новый фаил создан");
                } else shortMessage("Создание нового файла не удалось");
                return true;
            case R.id.controlBudgetActivityIntent:
                Intent intentBudget = new Intent(MainActivity.this, BudgetControlActivity.class);
                startActivity(intentBudget);
                return  true;

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
        mValutaView.append("\nConnection...\n");


        if (isNetworkConnected()) {
            mValutaView.append("\nПодключено\nЗагружаем...");

            AsyncParse AD = new AsyncParse();
            AD.execute();


        } else {
            mValutaView.append("\nНет соединения");
        }
    }


    public void loadButton_Click(View view) {
        loadPouchs();
    }

    private void loadPouchs(){
        AsyncSerialize AS = new AsyncSerialize();
        AS.execute();
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

    class AsyncSerialize extends AsyncTask<Void, Void, Void> {

        public ArrayList<Wallet> listWallets = null;

        @Override
        protected Void doInBackground(Void... params) {

            listWallets = new Serialzer(getApplicationContext()).readWallets();
            return null;
        }

   /*     private double searhValuta(String VAL){
            for (Valuta v : listValuta){
                if (v.name.equals(VAL)) return v.valueRUB;
            }
            return -1;
        }*/

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

         /*   for (Valuta v: listValuta){
            mPouchsView.append("\n" + v.name + "  -  " + v.valueRUB);
            }
            mPouchsView.append("\n\n");*/

            if (listWallets != null) {
               // mValutaView.append("\nЧтение прошло успешно!");
                for (Wallet p : listWallets) {
                    mPouchsView.append("\n"  + p.name + "   " + p.value + " " + p.valuta + "\n");
                    for (String str : p.listConvertibleValuta) {


                        double result = new Converter(listValuta).convertToValuta(p.valuta, p.value, str);
                        if(result!=-1) mPouchsView.append(str + " -  " + result + "   ,  ");
                        else  mPouchsView.append(str + "  ,  ");

                      /*  mPouchsView.append(str + ", \n");
                        mPouchsView.append(str + "   " +

                                +"  ,\n");*/


                    }
                    mPouchsView.append("\n");
                }
            } else mPouchsView.append("\n Произошла ошибка во время чтения");

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
                        searchValuta(doc.select("td.weak").first(), Pattern.compile("&nbsp;(.......)"), 1, "Доллар США: ")));

                SB.append("\n");
                listValuta.add(new Valuta("EUR",
                        searchValuta(doc.select("td.weak").last(), Pattern.compile("&nbsp;(.......)"), 1, "ЕВРО:  "))); // 7 символов

                SB.append("\n");
                searchValuta(doc.select("div.w_data_wrap").first(), Pattern.compile("</i>(.......)"), 1, "Доллар США завтра: ");
                //searchValuta(doc.select("div.w_data_wrap").first(), Pattern.compile("</i>(.......)"), 1, "Доллар США завтра: ");
                SB.append("\n");
                searchValuta(doc.body(), Pattern.compile("</i>(.......)"), 1, 6, "ЕВРО завтра: ");
                updated = true;

            } else
                SB.append("Ошибка");

            return null;

        }

        private double searchValuta(Element element, Pattern pattern, int group, String text) {
            //pattern = Pattern.compile("(?is)&nbsp;(.......)");
            String vtext;
            Matcher matcher = pattern.matcher(element.html());
            while (matcher.find()) {
                //  SB.append(matcher.group());
                SB.append(text);
                vtext = matcher.group(group)+"";
                SB.append(vtext  + " .руб \n");
                return Double.parseDouble(vtext.replaceAll(" ","").replace(',', '.')); // необходимо доработать и избавиться от запятой и пробелов в извлченном
                // значении
            }
            return -1;
        }
/*

        private String helpDouble(String text){
            char [] chars = text.toCharArray();
            StringBuilder SBUI = new StringBuilder();
            for (char c : chars){
                if(c == ',')SB.append('.');
                else SB.append(c);
            }
            return SBUI.toString();
        }
*/

        private double searchValuta(Element element, Pattern pattern, int group, int num, String text) {
            String vtext;
            Matcher matcher = pattern.matcher(element.html());
            int i = 0;
            while (matcher.find()) {
                //  SB.append(matcher.group());
                if (i == num) {
                    SB.append(text);
                    vtext = matcher.group(group)+"";
                    SB.append(vtext + " .руб \n");
                    return Double.parseDouble(vtext.replaceAll(" ","").replace(',','.'));
                }
                i++;
            }
            return -1;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mValutaView.setText(SB);
        }
    }
}
