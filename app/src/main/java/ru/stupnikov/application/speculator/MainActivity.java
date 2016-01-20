package ru.stupnikov.application.speculator;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;



import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.stupnikov.application.data.Pouch;
import ru.stupnikov.application.data.Serialzer;


public class MainActivity extends AppCompatActivity {

    private TextView mValutasView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mValutasView = (TextView)findViewById(R.id.valutas);


        final Button mOffButton = (Button)findViewById(R.id.startOffileButton);


        mOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isNetworkConnected()) {
                    mValutasView.append("Интернет включен");
                    
                } else {
                    mValutasView.append("Нет доступа к интернету!");
                }

                ArrayList<Pouch> listPouches = new ArrayList<Pouch>();
                listPouches.add(new Pouch("test1", 1, 1));
                listPouches.add(new Pouch("test2", 2, 2));
                listPouches.add(new Pouch("test4", 50, 3));

                Serialzer serialzer = new Serialzer();
                serialzer.write(listPouches);


            }
        });


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

        mValutasView.append("Connection...\n");


        if(isNetworkConnected()) {
            mValutasView.append("CONNECT?  YEP!");


           // AsyncSerialize AS = new AsyncSerialize();
           // AS.execute();
            AsyncParse AD = new AsyncParse();
            AD.execute();

        } else {
            mValutasView.append("  CONNECT?  NOPE!");
        }

    }

/*    class AsyncSerialize extends  AsyncTask<Void, Void, Void> {

        ArrayList<Pouch> listPouches;
        @Override
        protected Void doInBackground(Void... params) {
            Serialzer serialzer = new Serialzer();
             listPouches = serialzer.read();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            for(Pouch pouch : listPouches) {
                mValutasView.append(pouch.name, pouch.value, pouch.position);
            }
        }
    }*/

    class AsyncParse extends AsyncTask<Void, Void, Void>  {


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


            }
            else
                SB.append( "Ошибка");

            return null;

        }

        private void searchValuta(Element element, Pattern pattern, int group, String text) {
            //pattern = Pattern.compile("(?is)&nbsp;(.......)");
            Matcher matcher = pattern.matcher(element.html());
            while (matcher.find()){
                //  SB.append(matcher.group());
                SB.append(text);
                SB.append(matcher.group(group) + " .руб \n");
            }
        }

        private  void searchValuta (Element element, Pattern pattern, int group, int num, String text){
            Matcher matcher = pattern.matcher(element.html());
            int i = 0;
            while (matcher.find()){
                //  SB.append(matcher.group());
                if (i ==num)
                {
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

            mValutasView.setText( SB);
        }
    }
}
