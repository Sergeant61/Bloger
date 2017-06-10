package com.recep.bloger.adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.recep.bloger.R;
import com.recep.bloger.model.BasliklarReturn;
import com.recep.bloger.model.MesajModel;

import java.util.List;

/**
 * Created by vektorel on 03.06.2017.
 */
public class OzelAdapterMesaj extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<MesajModel> mMesajModel;

    public OzelAdapterMesaj(Activity activity, List<MesajModel> mesajModel) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mMesajModel = mesajModel;
    }

    @Override
    public int getCount() {
        return mMesajModel.size();
    }

    @Override
    public MesajModel getItem(int position) {
        //şöyle de olabilir: public Object getItem(int position)
        return mMesajModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View sag_mesaj,sol_mesaj;

        sag_mesaj = mInflater.inflate(R.layout.mesaj_sag_layout, null);
        sol_mesaj = mInflater.inflate(R.layout.mesaj_sol_layout, null);

        TextView sagMesaj = (TextView) sag_mesaj.findViewById(R.id.mesaj);
        TextView sagDate = (TextView) sag_mesaj.findViewById(R.id.saat);

        TextView solMesaj = (TextView) sol_mesaj.findViewById(R.id.mesaj);
        TextView solDate = (TextView) sol_mesaj.findViewById(R.id.saat);

        MesajModel mesajModel = mMesajModel.get(position);

        if (mesajModel.isMesajGonderen()){

            sagMesaj.setText(mesajModel.getMesaj());
            sagDate.setText(mesajModel.getTarih().toString());

            return sag_mesaj;
        } else {

            solMesaj.setText(mesajModel.getMesaj());
            solDate.setText(mesajModel.getTarih().toString());

            return sol_mesaj;
        }

    }
}
