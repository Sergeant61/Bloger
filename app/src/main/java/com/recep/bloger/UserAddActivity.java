package com.recep.bloger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.util.HashMap;
import java.util.Map;

public class UserAddActivity extends AppCompatActivity {

    private GsonConverter gsonConverter = new GsonConverter();
    private String URL = "http://192.168.1.60:8080/BlogWebServis/rest/servis/postKullAdd/";
    private EditText etKullAd,etPass1,etPass2;
    private Switch aSwitch1,aSwitch2;

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add);

        etKullAd = (EditText) findViewById(R.id.etKull);
        etPass1 = (EditText) findViewById(R.id.etPass1);
        etPass2 = (EditText) findViewById(R.id.etPass2);
        aSwitch1 = (Switch) findViewById(R.id.switch1);
        aSwitch2 = (Switch) findViewById(R.id.switch2);

    }

    public void onClickKaydet(View view) {

        switch (view.getId()){
            case R.id.btnKaydet:

                if(!etKullAd.getText().toString().equals("") && !etPass1.getText().toString().equals("") && !etPass2.getText().toString().equals("")){

                    if(etPass1.getText().toString().equals(etPass2.getText().toString())){

                        User user = new User();
                        user.setUsername(etKullAd.getText().toString());
                        user.setPassword(etPass1.getText().toString());
                        user.getRol().setRol_kullaniciIslemleri(aSwitch1.isChecked());
                        user.getRol().setRol_konuIslemleri(aSwitch2.isChecked());
                        String value = gsonConverter.getJson(user);
                        postKullAdd(URL,value);


                    } else {
                        new BounceAnimation(etPass1).setNumOfBounces(2).setDuration(Animation.DURATION_SHORT).animate();
                        new BounceAnimation(etPass2).setNumOfBounces(2).setDuration(Animation.DURATION_SHORT).animate();
                        Toast.makeText(this, "Girdiğiniz parolalar eşleşmiyor.", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    if (etKullAd.getText().toString().equals("")) {
                        new BounceAnimation(etKullAd).setNumOfBounces(2).setDuration(Animation.DURATION_SHORT).animate();
                        Toast.makeText(this, "Kullanıcı adı bölümü boş bırakılamaz.", Toast.LENGTH_SHORT).show();
                    }
                    if (etPass1.getText().toString().equals("") || etPass2.getText().toString().equals("")){
                        new BounceAnimation(etPass1).setNumOfBounces(2).setDuration(Animation.DURATION_SHORT).animate();
                        new BounceAnimation(etPass2).setNumOfBounces(2).setDuration(Animation.DURATION_SHORT).animate();
                        Toast.makeText(this, "Parola bölümü boş bırakılamaz.", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }

    }

    private void postKullAdd(String URL, final String value) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        User user = null;
                        if(!response.equals("YOK")){
                        user = gsonConverter.getStringUser(response);
                        }
                        if(user!=null){
                            Toast.makeText(UserAddActivity.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                            UserAddActivity.this.finish();
                        } else {
                            Toast.makeText(UserAddActivity.this, "Girdiğiniz kullanıcı adı kullanılmaktadır." +
                                    " Farklı bir kullanıcı adı girin.", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", "response"  + error.getLocalizedMessage());
                        Toast.makeText(UserAddActivity.this, "İnternet bağlantınızı kontrol ediniz.", Toast.LENGTH_LONG).show();
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
}
