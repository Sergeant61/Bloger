package com.recep.bloger.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.recep.bloger.model.BasliklarReturn;
import com.recep.bloger.model.UserReturn;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonConverter {

    public String getJson(Object object){

        Gson gson = new Gson();
        String json = gson.toJson(object);

        return json;
    }

    public UserReturn getStringUser(String json){

        Gson gson = new Gson();
        UserReturn userReturn = gson.fromJson(json,UserReturn.class);

        return userReturn;
    }

    public List<BasliklarReturn> getStringBasliklarReturnList(String json){

        Gson gson = new Gson();

        Type listType = new TypeToken<ArrayList<BasliklarReturn>>(){}.getType();

        List<BasliklarReturn> basliklarReturns =gson.fromJson(json,listType);


        return basliklarReturns;
    }

}
