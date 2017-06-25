package com.recep.bloger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.recep.bloger.converter.GsonConverter;
import com.recep.bloger.entity.Basliklar;
import com.recep.bloger.entity.User;

import java.util.HashMap;
import java.util.Map;

public class KonuAddActivity extends AppCompatActivity {

    private GsonConverter gsonConverter = new GsonConverter();
    private EditText etKonuBas, etKonu;
    private static String getResponse = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konu_add);

        Intent intent = getIntent();
        getResponse = intent.getStringExtra("user");

        etKonuBas = (EditText) findViewById(R.id.etKonuBas);
        etKonu = (EditText) findViewById(R.id.etKonu);

    }

    public void onClickKonuKaydet(View view) {

        switch (view.getId()) {
            case R.id.btnKonuKaydet:
                User user = gsonConverter.getStringUser(getResponse);

                Basliklar basliklar = new Basliklar();

                basliklar.setBaslik(etKonuBas.getText().toString());
                basliklar.setKonu(etKonu.getText().toString());
                basliklar.setUser(user);

                String value = gsonConverter.getJson(basliklar);
                String URL = getResources().getString(R.string.ipPort) + getResources().getString(R.string.postKonuAdd);
                post(URL, value);
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
                            KonuAddActivity.this.finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
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
}
