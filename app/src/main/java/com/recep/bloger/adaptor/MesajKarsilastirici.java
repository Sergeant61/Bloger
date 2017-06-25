package com.recep.bloger.adaptor;

import android.util.Log;

import com.recep.bloger.entity.Mesaj;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by recep on 23.06.2017.
 */

public class MesajKarsilastirici implements Comparator<Mesaj> {


    @Override
    public int compare(Mesaj mesaj1, Mesaj mesaj2) {

        Date tarih1 = mesaj1.getTarih();
        Date tarih2 = mesaj2.getTarih();

        if (tarih1.getTime() > tarih2.getTime()) {
            Log.e("HATA",tarih1.getTime() + " " +tarih2.getTime());
            return 1;
        } else if (tarih2.getTime() < tarih2.getTime()) {
            return -1;
        }


        return 0;
    }
}


