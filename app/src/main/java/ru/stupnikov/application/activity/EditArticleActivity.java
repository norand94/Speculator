package ru.stupnikov.application.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import ru.stupnikov.application.adapter.ArticleAdapter;
import ru.stupnikov.application.orm_classes.Category;
import ru.stupnikov.application.orm_classes.Subcategory;
import ru.stupnikov.application.speculator.R;

/**
 * Created by rodion on 27.01.16.
 */
public class EditArticleActivity extends AppCompatActivity {

    private EditText mEditCategory;
    private EditText mEditSubCategory;
    private ListView mArticleListView;


    //ArrayList<Article> listArticles = new ArrayList<Article>();
    List<Category> listCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_article);
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
        try {
            listCategory = Category.getListCategories();
            if (listCategory == null) {
                shortMessage("Неудачная загрузка категорий");
                // listCategory = new ArrayList<Category>();
                return;
            }

            updateArticlesViev();
        }catch (RuntimeException e) {
           // shortMessage("Неудачная загрузка категорий");
        } catch (Exception e2) {
          //  shortMessage("Неудачная загрузка категорий");

        }

    }

    private void updateArticlesViev()
    {
        mArticleListView.setAdapter(new ArticleAdapter(this, listCategory));
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
        else if (!mEditCategory.getText().toString().equals("") && mEditSubCategory.getText().toString().equals("")){
            addCategory();
        }
        else if (!mEditCategory.getText().toString().equals("") && !mEditSubCategory.getText().toString().equals("")){
            addSubcategory();
        }

    }

    private void addSubcategory() {
        Category selectedCategory = Category.findCategoryByName(mEditCategory.getText().toString());

        if (selectedCategory == null) {
            selectedCategory = new Category(mEditCategory.getText().toString());
            selectedCategory.save();

            saveSubcategory(selectedCategory);
        }
        else {
            saveSubcategory(selectedCategory);
        }

        loadArticles();
    }

    private void saveSubcategory(Category selectedCategory){
        Subcategory subcategory = new Subcategory(mEditSubCategory.getText().toString(), selectedCategory);
        subcategory.save();
        shortMessage("Подкатегория успешно записана");
    }



    private void addCategory() {
        Category category = Category.findCategoryByName(mEditCategory.getText().toString());

        if (category ==null) {
            category = new Category(mEditCategory.getText().toString());
            category.save();
        }
        else Toast.makeText(this, "Такая категория уже существует", Toast.LENGTH_SHORT).show();

        shortMessage("Категория успешно записана");
        loadArticles();
    }


    public void deleteArticleButton_Click(View view) {
        Category category = Category.findCategoryByName(mEditCategory.getText().toString());
        if (category == null) shortMessage("Нет такой категории!");
        else deleteCategory(category);
    }

    public void deleteCategory(Category category){
        List<Subcategory> subcategoryList = category.getListSubcategories();
        if (subcategoryList == null) category.delete();
        else  {
            for (Subcategory subcategory : subcategoryList)
                subcategory.delete();
            category.delete();
        }
        loadArticles();
    }
}
