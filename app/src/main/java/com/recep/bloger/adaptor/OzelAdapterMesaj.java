package com.recep.bloger.adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.recep.bloger.R;
import com.recep.bloger.entity.Mesaj;
import com.recep.bloger.model.BasliklarReturn;
import com.recep.bloger.model.MesajModel;

import java.util.List;

public class OzelAdapterMesaj extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Mesaj> mMesaj;

    public OzelAdapterMesaj(Activity activity, List<Mesaj> mesaj) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mMesaj = mesaj;
    }

    @Override
    public int getCount() {
        return mMesaj.size();
    }

    @Override
    public Mesaj getItem(int position) {
        //şöyle de olabilir: public Object getItem(int position)
        return mMesaj.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View sag_mesaj,sol_mesaj;

        sag_mesaj = mInflater.inflate(R.layout.mesaj_sag_layout, null);

        TextView sagMesaj = (TextView) sag_mesaj.findViewById(R.id.mesaj);
        TextView sagDate = (TextView) sag_mesaj.findViewById(R.id.saat);
        TextView etUser = (TextView) sag_mesaj.findViewById(R.id.etUser);

        Mesaj mesaj = mMesaj.get(position);

            sagMesaj.setText(mesaj.getMesaj());
            if(mesaj.getTarih() != null) {
                sagDate.setText(String.valueOf(mesaj.getTarih().getHours() + ":" + mesaj.getTarih().getMinutes()));
            }
            etUser.setText(mesaj.getUser().getUsername());
            return sag_mesaj;
    }
}
