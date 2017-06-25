package com.recep.bloger.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.recep.bloger.entity.Basliklar;
import com.recep.bloger.entity.Mesaj;
import com.recep.bloger.entity.User;
import com.recep.bloger.model.BasliklarReturn;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonConverter {

    public String getJson(Object object){

        Gson gson = new Gson();
        String json = gson.toJson(object);

        return json;
    }

    public User getStringUser(String json){

        Gson gson = new Gson();
        User user = gson.fromJson(json,User.class);

        return user;
    }

    public List<BasliklarReturn> getStringBasliklarReturnList(String json){

        Gson gson = new Gson();

        Type listType = new TypeToken<ArrayList<BasliklarReturn>>(){}.getType();

        List<BasliklarReturn> basliklarReturns =gson.fromJson(json,listType);


        return basliklarReturns;
    }

    public List<User> getStringUserList(String json){

        Gson gson = new Gson();

        Type listType = new TypeToken<ArrayList<User>>(){}.getType();

        List<User> users =gson.fromJson(json,listType);

        return users;
    }

    public Basliklar getStringBasliklar(String json) {
        Gson gson = new Gson();
        Basliklar basliklar = gson.fromJson(json,Basliklar.class);

        return basliklar;
    }

    public List<Basliklar> getStringBasliklarList(String json) {

        Gson gson = new Gson();

        Type listType = new TypeToken<ArrayList<Basliklar>>(){}.getType();

        List<Basliklar> basliklar = gson.fromJson(json,listType);


        return basliklar;
    }

    public List<Mesaj> getStringMesajList(String json) {

        Gson gson = new Gson();

        Type listType = new TypeToken<ArrayList<Mesaj>>(){}.getType();

        List<Mesaj> mesajs = gson.fromJson(json,listType);


        return mesajs;
    }
}
