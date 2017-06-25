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

    private GsonConverter gsonConverter = new GsonConverter();
    private String URL,URL2;
    private EditText etkullanici;
    private EditText etParola;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        URL = getResources().getString(R.string.ipPort) + getResources().getString(R.string.post);
        URL2 = getResources().getString(R.string.ipPort) + getResources().getString(R.string.postDao);
        postDao(URL2);
        etkullanici = (EditText) findViewById(R.id.etKullanici);
        etParola = (EditText) findViewById(R.id.etParola);

    }

    public void onClickGiris(View view) {
        switch (view.getId()) {
            case R.id.btnGiris:
                UserModel userModel = new UserModel();
                if (!etkullanici.getText().toString().equals("") && !etParola.getText().toString().equals("")) {

                    userModel.setKullaniciAdi(etkullanici.getText().toString());
                    userModel.setParola(etParola.getText().toString());
                    String value = gsonConverter.getJson(userModel);

                    post(URL, value);

                } else {
                    if (etkullanici.getText().toString().equals("")) {
                        new BounceAnimation(etkullanici).setNumOfBounces(2).setDuration(Animation.DURATION_SHORT).animate();
                        Toast.makeText(this, getResources().getString(R.string.user_check_hata_1), Toast.LENGTH_SHORT).show();
                    }

                    if (etParola.getText().toString().equals("")) {
                        new BounceAnimation(etParola).setNumOfBounces(2).setDuration(Animation.DURATION_SHORT).animate();
                        Toast.makeText(this, getResources().getString(R.string.user_check_hata_2), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }


    private void post(String URL, final String value) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        if (!response.equals(getResources().getString(R.string.yok))) {
                            Intent intent = new Intent(MainActivity.this, BasliklarActivity.class);
                            User user = gsonConverter.getStringUser(response);

                            String kull = user.getUsername();

                            intent.putExtra("user", response);

                            Toast.makeText(MainActivity.this, getResources().getString(R.string.hosgeldiniz) + " " + kull, Toast.LENGTH_LONG).show();
                            startActivity(intent);

                        } else {
                            Toast.makeText(MainActivity.this, R.string.user_check_hata_3, Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.hata_add_6), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("json", value);

                return params;
            }
        };
        queue.add(postRequest);
    }

    private void postDao(String URL) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        queue.add(postRequest);
    }
}
