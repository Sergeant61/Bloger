package com.recep.bloger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.recep.bloger.adaptor.MessageAdapter;
import com.recep.bloger.adaptor.OzelAdapterMesaj;
import com.recep.bloger.model.MesajModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private String kulAdi=null,id=null,baslik=null;
    RecyclerView recyclerView;
    EditText mesajInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView = (RecyclerView) findViewById(R.id.messages);
        mesajInput = (EditText) findViewById(R.id.message_input);
        Intent intent = getIntent();

        id = intent.getStringExtra("id");
        baslik =intent.getStringExtra("baslik");
        kulAdi =intent.getStringExtra("kullanici");

        ChatActivity.this.setTitle(baslik);

        Toast.makeText(this, id  + " " + baslik + " " +kulAdi, Toast.LENGTH_SHORT).show();

    }

    public void onClickGonder(View view) {

        List<MesajModel> mesajList = new ArrayList<>();

        mesajList.add(new MesajModel(mesajInput.getText().toString(), Calendar.getInstance().getTime(),true,1));

        MessageAdapter messageAdapter = new MessageAdapter(getApplicationContext(),mesajList);

        OzelAdapterMesaj ozelAdapterMesaj = new OzelAdapterMesaj(ChatActivity.this,mesajList);

        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.setAdapter(messageAdapter);

    }
}
