package ru.stupnikov.application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ru.stupnikov.application.orm_classes.Category;
import ru.stupnikov.application.orm_classes.Subcategory;
import ru.stupnikov.application.speculator.R;

/**
 * Created by rodion on 09.03.16.
 */
public class ArticleAdapter extends BaseAdapter {

    private Context context;
    private List<Category> listCategory;


    public  ArticleAdapter (Context context, List<Category> listCategory){
        super();
        this.context =context; this.listCategory =listCategory;
    }

    @Override
    public int getCount() {
        return listCategory.size();
    }

    @Override

    public Object getItem(int position) {
        return listCategory.get(position);
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

        Category category = listCategory.get(position);
        categoryView.setText(category.getName());
        List<Subcategory> subcategoryList = category.getListSubcategories();
        //  List<Subcategory> subcategoryList = category.getListSubcategory_new();
       // List<Subcategory> subcategoryList = category.getListSubcategories();

        if (subcategoryList != null)
        for (Subcategory subcategory : subcategoryList) subcategoryView.append(subcategory.getName() + "\n");

        return convertView;
    }
}
