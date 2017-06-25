package com.recep.bloger;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.recep.bloger.adaptor.OzelAdapter;
import com.recep.bloger.converter.GsonConverter;
import com.recep.bloger.entity.Basliklar;
import com.recep.bloger.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasliklarActivity extends AppCompatActivity {

    private GsonConverter gsonConverter = new GsonConverter();
    private String URL;
    private String URL2;
    private static String getResponse = null;
    private ListView listemiz;
    private List<Basliklar> basliklars;
    private User user;
    private OzelAdapter ozelAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        URL = getResources().getString(R.string.ipPort) + getResources().getString(R.string.getbasliklar);
        URL2 = getResources().getString(R.string.ipPort) + getResources().getString(R.string.postKonuSil);
        getBasliklar(URL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basliklar);

        Intent intent = getIntent();
        getResponse = intent.getStringExtra("user");
        user = gsonConverter.getStringUser(getResponse);
        listemiz = (ListView) findViewById(R.id.liste);
        getBasliklar(URL);

    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.basliklar_menu, menu);

        User user = gsonConverter.getStringUser(getResponse);

        if (!user.getRol().isRol_konuIslemleri()) {

            MenuItem item = menu.findItem(R.id.konuEkle);
            item.setVisible(false);
        }

        if (!user.getRol().isRol_kullaniciIslemleri()) {
            MenuItem item3 = menu.findItem(R.id.kullaniciEkle);
            item3.setVisible(false);
            MenuItem item4 = menu.findItem(R.id.kullaniciSil);
            item4.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.konuEkle:
                Intent inten = new Intent(BasliklarActivity.this, KonuAddActivity.class);
                inten.putExtra("user", getResponse);
                startActivity(inten);
                break;
            case R.id.kullaniciEkle:
                Intent intent = new Intent(BasliklarActivity.this, UserAddActivity.class);
                intent.putExtra("user", getResponse);
                startActivity(intent);
                break;
            case R.id.kullaniciSil:
                Intent intent2 = new Intent(BasliklarActivity.this, UserlistActivity.class);
                intent2.putExtra("user", getResponse);
                startActivity(intent2);
                break;
        }

        return true;
    }

    private void getBasliklar(String URL) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        basliklars = gsonConverter.getStringBasliklarList(response);

                        ozelAdapter = new OzelAdapter(BasliklarActivity.this, basliklars);

                        listemiz.setAdapter(ozelAdapter);

                        listemiz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                Basliklar basliklar = basliklars.get(i);

                                String baslik = gsonConverter.getJson(basliklar);

                                Intent intent = new Intent(BasliklarActivity.this, ChatActivity.class);
                                intent.putExtra("baslik", baslik);
                                intent.putExtra("user", getResponse);
                                startActivity(intent);

                            }
                        });

                        listemiz.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                                Basliklar basliklar = basliklars.get(i);

                                if (basliklar.getUser().getId() == user.getId() || user.getRol().isRol_konuIslemleri()) {
                                    Dialog dialog = new Dialog(i);
                                    dialog.dialogNeIster(BasliklarActivity.this);
                                }
                                return false;
                            }
                        });

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                    }
                }
        );
        queue.add(postRequest);
    }

    private class Dialog {

        int i;

        Dialog(int i) {
            this.i = i;
        }

        void dialogNeIster(final Activity activity) {

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            builder.setTitle(getResources().getString(R.string.dialog_app_title));
            builder.setMessage(getResources().getString(R.string.dialog_message_1));

            builder.setNegativeButton(getResources().getString(R.string.dialog_btn_sil), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialogSil(activity);

                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

        }

        void dialogSil(Activity activity) {

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            builder.setTitle(getResources().getString(R.string.dialog_app_title));
            builder.setMessage(getResources().getString(R.string.dialog_message_2));

            builder.setPositiveButton(getResources().getString(R.string.dialog_btn_evet), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Basliklar basliklar = basliklars.get(i);
                    String value = gsonConverter.getJson(basliklar);
                    postKonuSil(URL2, value);

                    basliklars.remove(i);
                    ozelAdapter.notifyDataSetChanged();

                    dialog.dismiss();
                }
            });

            builder.setNegativeButton(getResources().getString(R.string.dialog_btn_hayir), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

        }

        private void postKonuSil(String URL, final String value) {

            RequestQueue queue = Volley.newRequestQueue(BasliklarActivity.this);
            StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.equals(getResources().getString(R.string.yok))) {
                                Toast.makeText(BasliklarActivity.this, getResources().getString(R.string.islem_basarili), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(BasliklarActivity.this, getResources().getString(R.string.bit_hata_olustu), Toast.LENGTH_LONG).show();
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

}
