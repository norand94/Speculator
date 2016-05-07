package ru.stupnikov.application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ru.stupnikov.application.orm_classes.Wallet;
import ru.stupnikov.application.speculator.R;

/**
 * Created by rodion on 07.05.16.
 */
public class WalletAdapter extends BaseAdapter {

    private Context context;
    private List<Wallet> walletList;

    public WalletAdapter(List<Wallet> walletList, Context context) {
        super();
        this.walletList = walletList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return walletList.size();
    }

    @Override
    public Object getItem(int position) {
        return walletList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        TextView nameWalletView = (TextView)convertView.findViewById(R.id.item_wallet_NameWallet);
        TextView otherWalletView = (TextView)convertView.findViewById(R.id.item_wallet_OtherWallet);

        Wallet wallet = walletList.get(position);
        nameWalletView.setText(wallet.name);
        otherWalletView.setText(wallet.toString());

        return convertView;
    }
}
