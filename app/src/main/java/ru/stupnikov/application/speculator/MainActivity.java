package ru.stupnikov.application.speculator;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
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


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.stupnikov.application.controller.Edit_pouch_activity;
import ru.stupnikov.application.data.Pouch;
import ru.stupnikov.application.data.Serialzer;


public class MainActivity extends AppCompatActivity {

    private TextView mValutaView;
    private MotionEvent motionEvent;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mValutaView = (TextView) findViewById(R.id.valuta);
        if (isNetworkConnected()) {
            mValutaView.append("\nИнтернет включен");

        } else {
            mValutaView.append("\nНет доступа к интернету!");
        }

        final Button mSaveButton = (Button) findViewById(R.id.saveButton);


        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            /*    ArrayList<String> test = new ArrayList<String>();
                test.add( "EUR"); test.add("RUB");
                ArrayList<Pouch> listPouches = new ArrayList<Pouch>();
                listPouches.add(new Pouch("EUR", "EUR",  76, 1, test));
                listPouches.add(new Pouch("USD", "USD", 43, 2 ,test));
                listPouches.add(new Pouch("RUB", "RUB", 50, 3, test));

                Serialzer serialzer = new Serialzer(getApplicationContext());
                if (serialzer.writePouchs(listPouches)) {
                    mValutaView.append("\nУспешно записано!");
                } else mValutaView.append("\nпроизошла ошибка во время записи");*/

               /* if (serialzer.write(listPouches)) {
                    mValutaView.append("\nУспешно записано!");
                } else mValutaView.append("\nпроизошла ошибка во время записи");
*/
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
                Intent intent = new Intent(MainActivity.this, Edit_pouch_activity.class);
                startActivity(intent);
                return true;

            case R.id.repair_saving:
                Serialzer serialzer = new Serialzer(getApplicationContext());
                if (serialzer.createFile()) {
                    shortMessage("Новый фаил создан");
                } else shortMessage("Создание нового файла не удалось");
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }

/*    public boolean createFile() {
        try {


            FileOutputStream fOS = new FileOutputStream(Serialzer.FILE_POUCHS, MODE_WORLD_READABLE);
            OutputStreamWriter oSW = new OutputStreamWriter(fOS);
            oSW.write("test");
            oSW.flush();
            oSW.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }*/

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

        mValutaView.append("Connection...\n");


        if (isNetworkConnected()) {
            mValutaView.append("CONNECT?  YEP!");

            AsyncParse AD = new AsyncParse();
            AD.execute();


        } else {
            mValutaView.append("  CONNECT?  NOPE!");
        }

    }


    public void loadButton_Click(View view) {
        AsyncSerialize AS = new AsyncSerialize();
        AS.execute();
    }

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

        public ArrayList<Pouch> listPouches = null;

        @Override
        protected Void doInBackground(Void... params) {
            Serialzer serialzer = new Serialzer(getApplicationContext());
            listPouches = serialzer.readPouchs();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (listPouches != null) {
                mValutaView.append("\nЧтение прошло успешно!");
                for (Pouch p : listPouches) {
                    mValutaView.append("\n" + p.position + "-" + p.name + "   " + p.value + " " + p.valuta + "\n");
                    for (String str : p.listConvertibleValuta) {
                        mValutaView.append(str + ", ");
                    }
                    mValutaView.append("\n");
                }
            } else mValutaView.append("\n Произошла ошибка во время чтения");

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

                searchValuta(doc.select("td.weak").first(), Pattern.compile("&nbsp;(.......)"), 1, "Доллар США: ");
                SB.append("\n");
                searchValuta(doc.select("td.weak").last(), Pattern.compile("&nbsp;(.......)"), 1, "ЕВРО:  "); // 7 символов
                SB.append("\n");
                searchValuta(doc.select("div.w_data_wrap").first(), Pattern.compile("</i>(.......)"), 1, "Доллар США завтра: ");
                //searchValuta(doc.select("div.w_data_wrap").first(), Pattern.compile("</i>(.......)"), 1, "Доллар США завтра: ");
                SB.append("\n");
                searchValuta(doc.body(), Pattern.compile("</i>(.......)"), 1, 6, "ЕВРО завтра: ");


            } else
                SB.append("Ошибка");

            return null;

        }

        private void searchValuta(Element element, Pattern pattern, int group, String text) {
            //pattern = Pattern.compile("(?is)&nbsp;(.......)");
            Matcher matcher = pattern.matcher(element.html());
            while (matcher.find()) {
                //  SB.append(matcher.group());
                SB.append(text);
                SB.append(matcher.group(group) + " .руб \n");
            }
        }

        private void searchValuta(Element element, Pattern pattern, int group, int num, String text) {
            Matcher matcher = pattern.matcher(element.html());
            int i = 0;
            while (matcher.find()) {
                //  SB.append(matcher.group());
                if (i == num) {
                    SB.append(text);
                    SB.append(matcher.group(group) + " .руб \n");
                    break;
                }
                i++;
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mValutaView.setText(SB);
        }
    }
}
