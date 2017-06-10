package com.recep.bloger;

import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.recep.bloger.adaptor.OzelAdapter;
import com.recep.bloger.converter.GsonConverter;
import com.recep.bloger.model.BasliklarReturn;
import com.recep.bloger.model.RolEnum;

import java.util.List;


public class BasliklarActivity extends AppCompatActivity {

    private GsonConverter gsonConverter = new GsonConverter();
    private String URL = "http://192.168.1.60:8080/BlogWebServis/rest/servis/getbasliklar/";
    private static String kull=null,pass=null,roll=null;
    private ListView listemiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basliklar);

        Intent intent = getIntent();
        kull =intent.getStringExtra("user");
        pass =intent.getStringExtra("pass");
        roll =intent.getStringExtra("roll");

        listemiz = (ListView) findViewById(R.id.liste);

        getBasliklar(URL);

    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.basliklar_menu, menu);

        if(!RolEnum.ADMIN.getId().equals(roll)){

            MenuItem item = menu.findItem(R.id.konuEkle);
            item.setVisible(false);
            MenuItem item2 = menu.findItem(R.id.konuSil);
            item2.setVisible(false);
        }


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.konuEkle:

                break;
            case R.id.konuSil:

                break;
        }

        return true;
    }


    private void getBasliklar(String URL){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        final List<BasliklarReturn> basliklarReturns = gsonConverter.getStringBasliklarReturnList(response);

                        OzelAdapter ozelAdapter = new OzelAdapter(BasliklarActivity.this, basliklarReturns);

                        listemiz.setAdapter(ozelAdapter);

                        listemiz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                BasliklarReturn basliklarReturn = new BasliklarReturn();

                                basliklarReturn = basliklarReturns.get(i);

                                String aa = String.valueOf(basliklarReturn.getId());

                                Intent intent = new Intent(BasliklarActivity.this,ChatActivity.class);
                                intent.putExtra("id",aa);
                                intent.putExtra("baslik",basliklarReturn.getBaslik());
                                intent.putExtra("kullanici",basliklarReturn.getKullaniciAdi());
                                startActivity(intent);

                            }
                        });

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", "response");
                    }
                }
        );
        queue.add(postRequest);
    }

}
