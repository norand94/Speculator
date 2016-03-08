package ru.stupnikov.application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ru.stupnikov.application.data.Article;
import ru.stupnikov.application.speculator.R;

/**
 * Created by rodion on 09.03.16.
 */
public class ArticleAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Article> listArticle;


    public  ArticleAdapter (Context context, ArrayList<Article> listArticle){
        super(); this.context =context; this.listArticle =listArticle;
    }

    @Override
    public int getCount() {
        return listArticle.size();
    }

    @Override
    public Object getItem(int position) {
        return listArticle.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.item_article_listview, parent, false);
        TextView categoryView = (TextView)convertView.findViewById(R.id.categoryTextView);
        TextView subcategoryView = (TextView)convertView.findViewById(R.id.subcategoryTextView);

        Article article = listArticle.get(position);
        categoryView.setText(article.category);
        for (String subcategory : article.listSubCategory) subcategoryView.append(subcategory + "\n");


        return convertView;
    }
}
