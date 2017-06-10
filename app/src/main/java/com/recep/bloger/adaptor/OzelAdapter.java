package com.recep.bloger.adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.recep.bloger.R;
import com.recep.bloger.model.BasliklarReturn;

import java.util.List;

/**
 * Created by vektorel on 03.06.2017.
 */
public class OzelAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<BasliklarReturn> mBasliklarReturn;

    public OzelAdapter(Activity activity, List<BasliklarReturn> basliklarReturn) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mBasliklarReturn = basliklarReturn;
    }

    @Override
    public int getCount() {
        return mBasliklarReturn.size();
    }

    @Override
    public BasliklarReturn getItem(int position) {
        //şöyle de olabilir: public Object getItem(int position)
        return mBasliklarReturn.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satirView;

        satirView = mInflater.inflate(R.layout.satir_layout, null);
        TextView baslik =
                (TextView) satirView.findViewById(R.id.baslik);
        TextView user =
                (TextView) satirView.findViewById(R.id.user);


        BasliklarReturn basliklarReturn = mBasliklarReturn.get(position);

        baslik.setText(basliklarReturn.getBaslik());
        user.setText(basliklarReturn.getKullaniciAdi());

        return satirView;
    }
}
