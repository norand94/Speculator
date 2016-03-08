package ru.stupnikov.application.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ru.stupnikov.application.adapter.ArticleAdapter;
import ru.stupnikov.application.data.Article;
import ru.stupnikov.application.processor.Serialzer;
import ru.stupnikov.application.speculator.R;

/**
 * Created by rodion on 27.01.16.
 */
public class EditArticleActivity extends AppCompatActivity {

    private EditText mEditCategory;
    private EditText mEditSubCategory;
    private ListView mArticleListView;


    ArrayList<Article> listArticles = new ArrayList<Article>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_article_activity);
        mEditCategory =(EditText)findViewById(R.id.editCategory);
        mEditSubCategory = (EditText)findViewById(R.id.editSubCategory);
        mArticleListView = (ListView)findViewById(R.id.articleListView);

        loadArticles();

        mArticleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                shortMessage("OK!");
            }
        });
    }

    private void loadArticles(){
        listArticles = (ArrayList<Article>) Serialzer.readObject(getApplicationContext(), Serialzer.FILE_ARTICLES);
        if (listArticles == null){
           // shortMessage("Неудачная загрузка артиклей");
            listArticles = new ArrayList<Article>();
        }
        updateArticlesViev();

    }

    private void updateArticlesViev()
    {

        mArticleListView.setAdapter(new ArticleAdapter(this, listArticles));
    /*mArticleView.setText("");
        for (Article article : listArticles){
            mArticleView.append(article.category);
                mArticleView.append(":");
                for (String sub : article.listSubCategory){
                    mArticleView.append("\n-- " + sub);
                }

                mArticleView.append("\n");

        }*/
    }

    private boolean saveArticles(){
        if(Serialzer.writeObject(getApplicationContext(), listArticles, Serialzer.FILE_ARTICLES)){
            return true;
        } else return false;
    }

    private void shortMessage(String text){
        Toast.makeText (getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void addArticleButton_Click(View view) {


        if (mEditCategory.getText().toString().equals("") && mEditSubCategory.getText().toString().equals("")){
            shortMessage("Введите текст!");
        }
        else if ( mEditCategory.getText().toString().equals("") &&  !mEditSubCategory.getText().toString().equals("")){
            shortMessage("У подкатегории должна быть главная категория!");
        }
        else if (!mEditCategory.getText().toString().equals("") &&  mEditSubCategory.getText().toString().equals("")){
            if (Article.searhArticle(listArticles, mEditCategory.getText().toString())==null){
            listArticles.add(new Article(mEditCategory.getText().toString(), new ArrayList<String>()));
            } else shortMessage("Такая категория уже существует");

        }
        else if (!mEditCategory.getText().toString().equals("") &&  !mEditSubCategory.getText().toString().equals("")){
            Article article =  Article.searhArticle(listArticles, mEditCategory.getText().toString());
            ArrayList<String> listSub = new ArrayList<>();
            listSub.add(mEditSubCategory.getText().toString());

            if (article == null) {
                listArticles.add(new Article(mEditCategory.getText().toString(), listSub));
            } else {
                if (article.searh_subcategory(mEditSubCategory.getText().toString())){
                    shortMessage("такая статья уже существует");
                } else {
                    listArticles.remove(article);
                    article.listSubCategory.add(mEditSubCategory.getText().toString());
                    listArticles.add(article);
                }
            }
        }
        saveArticles();
        updateArticlesViev();

    }




    public void deleteArticleButton_Click(View view) {

    listArticles.remove(Article.searhArticle(listArticles, mEditCategory.getText().toString()));
        saveArticles();
        updateArticlesViev();

    }
}
