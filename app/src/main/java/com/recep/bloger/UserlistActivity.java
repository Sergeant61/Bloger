package com.recep.bloger;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.recep.bloger.adaptor.KullanciListAdapter;
import com.recep.bloger.converter.GsonConverter;
import com.recep.bloger.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserlistActivity extends AppCompatActivity {

    private GsonConverter gsonConverter = new GsonConverter();
    private String URL;
    private String URLsil;
    private String userText;
    private ListView kullaniciList;
    private List<User> users;
    private User user;

    @Override
    protected void onStart() {
        super.onStart();
        postGetUserList(URL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);

        URL = String.valueOf(getResources().getString(R.string.ipPort) + getResources().getString(R.string.postKullList));
        URLsil = getResources().getString(R.string.ipPort) + getResources().getString(R.string.postsil);

        Intent intent = getIntent();

        userText = intent.getStringExtra("user");
        this.user = gsonConverter.getStringUser(userText);

        kullaniciList = (ListView) findViewById(R.id.kullaniciList);
        postGetUserList(URL);

    }

    private void postGetUserList(String URL) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        if (!response.equals(getResources().getString(R.string.yok))) {
                            users = gsonConverter.getStringUserList(response);

                            KullanciListAdapter kullanciListAdapter = new KullanciListAdapter(UserlistActivity.this, users);
                            kullaniciList.setAdapter(kullanciListAdapter);

                            kullaniciList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    Dialog dialog = new Dialog(i);
                                    dialog.dialogNeIster(UserlistActivity.this);
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

    private class Dialog {

        int i;

        Dialog(int i) {
            this.i = i;
        }

        void dialogNeIster(final Activity activity) {

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            builder.setTitle(getResources().getString(R.string.dialog_app_title));
            builder.setMessage(getResources().getString(R.string.dialog_message_1));

            builder.setPositiveButton(getResources().getString(R.string.dialog_btn_guncelle), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialogGuncelle(activity);

                    dialog.dismiss();
                }
            });

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

                    User user = users.get(i);

                    String value = gsonConverter.getJson(user);

                    postUserSil(URLsil, value);

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

        void dialogGuncelle(Activity activity) {

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            builder.setTitle(getResources().getString(R.string.dialog_app_title));
            builder.setMessage(getResources().getString(R.string.dialog_message_3));

            builder.setPositiveButton(getResources().getString(R.string.dialog_btn_evet), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    User user = users.get(i);

                    String userGun = gsonConverter.getJson(user);

                    Intent intent = new Intent(UserlistActivity.this, UserAddActivity.class);
                    intent.putExtra("user", userText);
                    intent.putExtra("userGun", userGun);
                    intent.putExtra("isGun", true);
                    startActivity(intent);

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

        private void postUserSil(String URL, final String value) {

            RequestQueue queue = Volley.newRequestQueue(UserlistActivity.this);
            StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.equals(getResources().getString(R.string.yok))) {
                                Toast.makeText(UserlistActivity.this, getResources().getString(R.string.islem_basarili), Toast.LENGTH_SHORT).show();

                                UserlistActivity.this.finish();

                            } else {
                                Toast.makeText(UserlistActivity.this, getResources().getString(R.string.dialog_hata_1), Toast.LENGTH_LONG).show();
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
