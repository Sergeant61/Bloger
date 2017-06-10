package com.recep.bloger.servis;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by recep on 5.06.2017.
 */

public class Servis {

    public class post extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            String veri = strings[0];

            try {

                URL url = new URL(veri);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    BufferedReader buf = new BufferedReader
                            (new InputStreamReader(connection.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String satir ;

                    while ((satir = buf.readLine()) !=  null){
                        sb.append(satir);
                    }
                    return sb.toString();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
