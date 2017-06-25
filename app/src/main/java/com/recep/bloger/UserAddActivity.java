package com.recep.bloger;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
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
import com.recep.bloger.entity.UserKullAdd;

import java.util.HashMap;
import java.util.Map;

public class UserAddActivity extends AppCompatActivity {

    private GsonConverter gsonConverter = new GsonConverter();
    private String URL;
    private String URL2;
    private EditText etKullAd, etPass1, etPass2;
    private Switch aSwitch1, aSwitch2;
    private static String getResponse = null;
    private User user, userGun;

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add);

        URL = getResources().getString(R.string.ipPort) + getResources().getString(R.string.postKullAdd);
        URL2 = getResources().getString(R.string.ipPort) + getResources().getString(R.string.postKullUpdate);

        etKullAd = (EditText) findViewById(R.id.etKull);
        etPass1 = (EditText) findViewById(R.id.etPass1);
        etPass2 = (EditText) findViewById(R.id.etPass2);
        aSwitch1 = (Switch) findViewById(R.id.switch1);
        aSwitch2 = (Switch) findViewById(R.id.switch2);
        Button btnKaydet = (Button) findViewById(R.id.btnKaydet);
        Button btnGuncelle = (Button) findViewById(R.id.btnGuncelle);

        Intent intent = getIntent();
        getResponse = intent.getStringExtra("user");
        String getUserGun = intent.getStringExtra("userGun");
        boolean isGun = intent.getBooleanExtra("isGun", false);

        user = gsonConverter.getStringUser(getResponse);
        userGun = gsonConverter.getStringUser(getUserGun);

        if (isGun) {
            btnGuncelle.setVisibility(View.VISIBLE);
            btnKaydet.setVisibility(View.GONE);
            UserAddActivity.this.setTitle(getString(R.string.kullanici_gun));

            etKullAd.setText(userGun.getUsername());
            etPass1.setText(userGun.getPassword());
            etPass2.setText(userGun.getPassword());
            aSwitch1.setChecked(userGun.getRol().isRol_kullaniciIslemleri());
            aSwitch2.setChecked(userGun.getRol().isRol_konuIslemleri());
        }

    }

    public void onClickKaydet(View view) {

        switch (view.getId()) {
            case R.id.btnKaydet:

                if (!etKullAd.getText().toString().equals("") && !etPass1.getText().toString().equals("") && !etPass2.getText().toString().equals("")) {

                    if (etPass1.getText().toString().equals(etPass2.getText().toString())) {

                        User user = new User();
                        user.setUsername(etKullAd.getText().toString());
                        user.setPassword(etPass1.getText().toString());
                        user.getRol().setRol_kullaniciIslemleri(aSwitch1.isChecked());
                        user.getRol().setRol_konuIslemleri(aSwitch2.isChecked());

                        User user1 = gsonConverter.getStringUser(getResponse);

                        UserKullAdd userKullAdd = new UserKullAdd();

                        userKullAdd.setId(user1.getId());
                        userKullAdd.setUsername(user1.getUsername());
                        userKullAdd.setRol(user1.getRol());

                        user.setUserKullAdd(userKullAdd);

                        String value = gsonConverter.getJson(user);
                        postKullAdd(URL, value);


                    } else {
                        new BounceAnimation(etPass1).setNumOfBounces(2).setDuration(Animation.DURATION_SHORT).animate();
                        new BounceAnimation(etPass2).setNumOfBounces(2).setDuration(Animation.DURATION_SHORT).animate();
                        Toast.makeText(this, getResources().getString(R.string.hata_add_1), Toast.LENGTH_SHORT).show();
                    }

                } else {

                    if (etKullAd.getText().toString().equals("")) {
                        new BounceAnimation(etKullAd).setNumOfBounces(2).setDuration(Animation.DURATION_SHORT).animate();
                        Toast.makeText(this, getResources().getString(R.string.hata_add_2), Toast.LENGTH_SHORT).show();
                    }
                    if (etPass1.getText().toString().equals("") || etPass2.getText().toString().equals("")) {
                        new BounceAnimation(etPass1).setNumOfBounces(2).setDuration(Animation.DURATION_SHORT).animate();
                        new BounceAnimation(etPass2).setNumOfBounces(2).setDuration(Animation.DURATION_SHORT).animate();
                        Toast.makeText(this, getResources().getString(R.string.hata_add_3), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btnGuncelle:

                if (!etKullAd.getText().toString().equals("") && !etPass1.getText().toString().equals("") && !etPass2.getText().toString().equals("")) {

                    if (etPass1.getText().toString().equals(etPass2.getText().toString())) {

                        userGun.setUsername(etKullAd.getText().toString());
                        userGun.setPassword(etPass1.getText().toString());
                        userGun.getRol().setRol_kullaniciIslemleri(aSwitch1.isChecked());
                        userGun.getRol().setRol_konuIslemleri(aSwitch2.isChecked());

                        UserKullAdd userKullAdd = new UserKullAdd();

                        userKullAdd.setId(user.getId());
                        userKullAdd.setUsername(user.getUsername());
                        userKullAdd.setRol(user.getRol());

                        userGun.setUserKullAdd(userKullAdd);

                        String value = gsonConverter.getJson(userGun);
                        postKullUpdate(URL2, value);


                    } else {
                        new BounceAnimation(etPass1).setNumOfBounces(2).setDuration(Animation.DURATION_SHORT).animate();
                        new BounceAnimation(etPass2).setNumOfBounces(2).setDuration(Animation.DURATION_SHORT).animate();
                        Toast.makeText(this, getResources().getString(R.string.hata_add_1), Toast.LENGTH_SHORT).show();
                    }

                } else {

                    if (etKullAd.getText().toString().equals("")) {
                        new BounceAnimation(etKullAd).setNumOfBounces(2).setDuration(Animation.DURATION_SHORT).animate();
                        Toast.makeText(this, getResources().getString(R.string.hata_add_2), Toast.LENGTH_SHORT).show();
                    }
                    if (etPass1.getText().toString().equals("") || etPass2.getText().toString().equals("")) {
                        new BounceAnimation(etPass1).setNumOfBounces(2).setDuration(Animation.DURATION_SHORT).animate();
                        new BounceAnimation(etPass2).setNumOfBounces(2).setDuration(Animation.DURATION_SHORT).animate();
                        Toast.makeText(this, getResources().getString(R.string.hata_add_3), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void postKullAdd(String URL, final String value) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        User user = null;
                        if (!response.equals(getResources().getString(R.string.yok))) {
                            user = gsonConverter.getStringUser(response);
                        }
                        if (user != null) {
                            Toast.makeText(UserAddActivity.this, getResources().getString(R.string.kayit_basarili), Toast.LENGTH_SHORT).show();
                            UserAddActivity.this.finish();
                        } else {
                            Toast.makeText(UserAddActivity.this, getResources().getString(R.string.hata_add_4), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserAddActivity.this, getResources().getString(R.string.hata_add_5), Toast.LENGTH_LONG).show();
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

    private void postKullUpdate(String URL, final String value) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        User user = null;
                        if (!response.equals(getResources().getString(R.string.yok))) {
                            user = gsonConverter.getStringUser(response);
                        }
                        if (user != null) {
                            Toast.makeText(UserAddActivity.this, getResources().getString(R.string.kayit_basarili), Toast.LENGTH_SHORT).show();
                            UserAddActivity.this.finish();
                        } else {
                            Toast.makeText(UserAddActivity.this, getResources().getString(R.string.hata_add_4), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserAddActivity.this, getResources().getString(R.string.hata_add_6), Toast.LENGTH_LONG).show();
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
