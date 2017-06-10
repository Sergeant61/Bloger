package com.recep.bloger.model;

import java.util.Date;

/**
 * Created by recep on 9.06.2017.
 */

public class MesajModel {

    private String mesaj;
    private Date tarih;
    private boolean mesajGonderen;
    private int type;

    public MesajModel() {
    }

    public MesajModel(String mesaj, Date tarih,boolean mesajGonderen,int type) {
        this.mesaj = mesaj;
        this.tarih = tarih;
        this.mesajGonderen = mesajGonderen;
        this.type = type;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public Date getTarih() {
        return tarih;
    }

    public void setTarih(Date tarih) {
        this.tarih = tarih;
    }

    public Boolean isMesajGonderen() {
        return mesajGonderen;
    }

    public void setMesajGonderen(Boolean mesajGonderen) {
        this.mesajGonderen = mesajGonderen;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
