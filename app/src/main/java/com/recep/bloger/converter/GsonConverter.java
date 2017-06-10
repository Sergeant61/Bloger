package com.recep.bloger.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

}
