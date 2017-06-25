package com.recep.bloger;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.recep.bloger.adaptor.MesajKarsilastirici;
import com.recep.bloger.adaptor.OzelAdapterMesaj;
import com.recep.bloger.converter.GsonConverter;
import com.recep.bloger.entity.Basliklar;
import com.recep.bloger.entity.Mesaj;
import com.recep.bloger.entity.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private GsonConverter gsonConverter = new GsonConverter();
    private String URL2;
    private String URL3;
    private ListView listView;
    private EditText mesajInput;
    private Basliklar basliklar;
    private User user;
    private OzelAdapterMesaj ozelAdapterMesaj;
    private List<Mesaj> mesajList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        listView = (ListView) findViewById(R.id.messages);
        mesajInput = (EditText) findViewById(R.id.message_input);
        TextView twKonu = (TextView) findViewById(R.id.twKonu);

        Intent intent = getIntent();

        String baslikText = intent.getStringExtra("baslik");
        String userText = intent.getStringExtra("user");
        User getUser = gsonConverter.getStringUser(userText);
        Basliklar getBasliklar = gsonConverter.getStringBasliklar(baslikText);
        this.basliklar = getBasliklar;
        this.user = getUser;

        String baslik = getBasliklar.getBaslik();
        twKonu.setText(getBasliklar.getKonu());
        ChatActivity.this.setTitle(baslik);

        mesajList = new ArrayList<>();
        String URL = getResources().getString(R.string.ipPort) + getResources().getString(R.string.postGetMesajList);
        URL2 = getResources().getString(R.string.ipPort) + getResources().getString(R.string.postMesajAdd);
        URL3 = getResources().getString(R.string.ipPort) + getResources().getString(R.string.postMesajSil);
        postGetMesajList(URL);


    }

    private void postGetMesajList(String URL) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        if (!response.equals(getResources().getString(R.string.yok))) {

                            List<Mesaj> mesajs = gsonConverter.getStringMesajList(response);

                            for (int i = 0; i < mesajs.size(); i++) {
                                if (mesajs.get(i).getBasliklars().getId() == basliklar.getId()) {
                                    mesajList.add(new Mesaj(mesajs.get(i).getId(), mesajs.get(i).getMesaj(), mesajs.get(i).getTarih(),
                                            mesajs.get(i).getBasliklars(), mesajs.get(i).getUser()));
                                }
                            }

                            Mesaj mesajkucuk;

                            for (int i = 0; i < mesajList.size(); i++) {
                                for (int k = 0; k < mesajList.size(); k++) {
                                    if(mesajList.get(i).getTarih().getTime() < mesajList.get(k).getTarih().getTime()){
                                        mesajkucuk=mesajList.get(i);
                                        mesajList.set(i,mesajList.get(k));
                                        mesajList.set(k,mesajkucuk);
                                    }
                                }
                            }

                            ozelAdapterMesaj = new OzelAdapterMesaj(ChatActivity.this, mesajList);
                            listView.setAdapter(ozelAdapterMesaj);

                            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                @Override
                                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    Mesaj mesaj = mesajList.get(i);
                                    if (mesaj.getUser().getId() == user.getId() || user.getRol().isRol_konuIslemleri()) {
                                        Dialog dialog = new Dialog(i);
                                        dialog.dialogNeIster(ChatActivity.this);
                                    }
                                    return false;
                                }
                            });


                        }
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

    public void onClickGonder(View view) {

        Mesaj mesaj = new Mesaj(mesajInput.getText().toString(), Calendar.getInstance().getTime(), basliklar, user);

        String value = gsonConverter.getJson(mesaj);

        postMesajAdd(URL2, value);

        mesajList.add(mesaj);
        ozelAdapterMesaj.notifyDataSetChanged();
        mesajInput.setText("");
    }

    private void postMesajAdd(String URL, final String value) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        if (!response.equals(getResources().getString(R.string.yok))) {
                            System.out.println("");
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

            builder.setPositiveButton(getResources().getString(R.string.dialog_btn_sil), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Mesaj mesaj = mesajList.get(i);

                    String value = gsonConverter.getJson(mesaj);

                    postMesajSil(URL3, value);

                    mesajList.remove(i);
                    ozelAdapterMesaj.notifyDataSetChanged();
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

        private void postMesajSil(String URL, final String value) {

            RequestQueue queue = Volley.newRequestQueue(ChatActivity.this);
            StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.equals(getResources().getString(R.string.yok))) {
                                Toast.makeText(ChatActivity.this, getResources().getString(R.string.islem_basarili), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ChatActivity.this, getResources().getString(R.string.bit_hata_olustu), Toast.LENGTH_LONG).show();
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
