package ru.stupnikov.application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
    public int getCount() {
        return listValutaCon.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_valuta_listview, parent, false);

        TextView nameTextView = (TextView)convertView.findViewById(R.id.nameValutaTextView);
        TextView valueTextView1 = (TextView)convertView.findViewById(R.id.valueValutaTextView1);
        TextView valueTextView2 = (TextView) convertView.findViewById(R.id.valueValutaTextView2);

        nameTextView.setText(listValutaCon.get(position).name);
        valueTextView1.setText(listValutaCon.get(position).getValue1_toString());
        valueTextView2.setText(listValutaCon.get(position).getValue2_toString());
        /*
        * final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.test_list_item, parent, false);
        TextView textView = (TextView) convertView.findViewById(R.id.textView);
        textView.setText(data.get(position).text);
        final Button button = (Button)convertView.findViewById(R.id.button);
        button.setBackgroundColor(context.getResources().getColor(data.get(position).color));

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SecondActivity.class);
                intent.putExtra(KEY_POSITION, position + "");
                intent.putExtra(KEY_COLOR, data.get(position).color);
                context.startActivity(intent);
            }
        });

        return convertView;
        * */

        return convertView;
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
