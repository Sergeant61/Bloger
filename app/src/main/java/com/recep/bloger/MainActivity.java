package com.recep.bloger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.easyandroidanimations.library.Animation;
import com.easyandroidanimations.library.BounceAnimation;
import com.recep.bloger.converter.GsonConverter;
import com.recep.bloger.entity.User;
import com.recep.bloger.model.UserModel;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private String URL = "http://192.168.1.60:8080/BlogWebServis/rest/servis/post/";
    private String URL2 = "http://192.168.1.60:8080/BlogWebServis/rest/servis/postDao/";
    private EditText etkullanici;
    private EditText etParola;
    private UserModel userModel;
    private GsonConverter gsonConverter = new GsonConverter();

    @Override
    protected void onStart() {
        super.onStart();
        postDao(URL2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etkullanici = (EditText) findViewById(R.id.etKullanici);
        etParola = (EditText) findViewById(R.id.etParola);

    }

    public void onClickGiris(View view) {
        switch (view.getId()){
            case R.id.btnGiris:
                userModel = new UserModel();
                if(!etkullanici.getText().toString().equals("") && !etParola.getText().toString().equals("")) {

                    userModel.setKullaniciAdi(etkullanici.getText().toString());
                    userModel.setParola(etParola.getText().toString());
                    String value = gsonConverter.getJson(userModel);

                    post(URL, value);

                } else {
                    if(etkullanici.getText().toString().equals("")) {
                        new BounceAnimation(etkullanici).setNumOfBounces(2).setDuration(Animation.DURATION_SHORT).animate();
                        Toast.makeText(this, "Kullanıcı adı bölümü boş bırakılamaz.", Toast.LENGTH_SHORT).show();
                    }

                    if(etParola.getText().toString().equals("")) {
                        new BounceAnimation(etParola).setNumOfBounces(2).setDuration(Animation.DURATION_SHORT).animate();
                        Toast.makeText(this, "Parola bölümü boş bırakılamaz.", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }


    private void post(String URL, final String value){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        //Log.d("RECEP", response);
                        if(!response.equals("YOK")){
                            Intent intent = new Intent(MainActivity.this,BasliklarActivity.class);
                            User user = gsonConverter.getStringUser(response);

                            String kull = user.getUsername();

                            intent.putExtra("user",response);

                            Toast.makeText(MainActivity.this, "Hoşgeldiniz " + kull, Toast.LENGTH_SHORT).show();
                            startActivity(intent);

                        } else {
                            Toast.makeText(MainActivity.this, "Kullanıcı adı veya Parola hatalı.", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", "response");
                        Toast.makeText(MainActivity.this, "İnternet bağlantınızı kontrol ediniz.", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("json", value);

                return params;
            }
        };
        queue.add(postRequest);
    }

    private void postDao(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        queue.add(postRequest);
    }
}
