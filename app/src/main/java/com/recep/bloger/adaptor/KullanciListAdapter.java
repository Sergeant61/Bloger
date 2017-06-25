package com.recep.bloger.adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.recep.bloger.R;
import com.recep.bloger.entity.User;
import com.recep.bloger.model.BasliklarReturn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KullanciListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater mInflater;
    private List<User> users;

    public KullanciListAdapter(Activity activity, List<User> user) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        this.activity = activity;
        this.users = user;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User getItem(int position) {
        //şöyle de olabilir: public Object getItem(int position)
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satirView;

        satirView = mInflater.inflate(R.layout.kull_list_satir_layout, null);

        TextView userTv =
                (TextView) satirView.findViewById(R.id.userTv);

        TextView kayitEdenTv =
                (TextView) satirView.findViewById(R.id.kayitEdenTv);

        TextView lvUserRolTv =
                (TextView) satirView.findViewById(R.id.lvUserRolTv);

        User user = users.get(position);

        userTv.setText(user.getUsername());
        kayitEdenTv.setText("("+ user.getUserKullAdd().getUsername() +")");

        String text;

            if(user.getRol().isRol_kullaniciIslemleri()){
                text= "Kullanıcı İşlemleri (True)";
            } else {
                text= "Kullanıcı İşlemleri (False)";
            }

            if(user.getRol().isRol_konuIslemleri()){
                text= text + "\nKonu İşlemleri (True)";
            } else {
                text= text + "\nKonu İşlemleri (False)";
            }
        lvUserRolTv.setText(text);

        return satirView;
    }
}
