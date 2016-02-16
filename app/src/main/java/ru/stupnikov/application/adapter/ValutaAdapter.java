package ru.stupnikov.application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ru.stupnikov.application.speculator.R;

/**
 * Created by rodion on 16.02.16.
 */
public class ValutaAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ValutaContainer> listValutaCon;

    public ValutaAdapter(Context context, ArrayList<ValutaContainer> listValutaCon) {
        super(); this.context = context; this.listValutaCon = listValutaCon;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_valuta_listview, parent, false);

        TextView nameTextView = (TextView)convertView.findViewById(R.id.nameValutaTextView);
        TextView valueTextView1 = (TextView)convertView.findViewById(R.id.valueValutaTextView1);
        TextView valueTextView2 = (TextView) convertView.findViewById(R.id.valueValutaTextView2);
        ImageView valutaIcon = (ImageView) convertView.findViewById(R.id.imageViewValutaIcon);

        nameTextView.setText(listValutaCon.get(position).name);
        valueTextView1.setText(listValutaCon.get(position).getValue1_toString());
        valueTextView2.setText(listValutaCon.get(position).getValue2_toString());
        valutaIcon.setImageResource(listValutaCon.get(position).icon);

        return convertView;
    }

    @Override
    public int getCount() {
        return listValutaCon.size();
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return listValutaCon.get(position);
    }
}
