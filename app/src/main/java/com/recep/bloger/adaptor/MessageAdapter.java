package com.recep.bloger.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.recep.bloger.R;
import com.recep.bloger.model.MesajModel;

import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<MesajModel> mMessages;

    public MessageAdapter(Context context, List<MesajModel> messages) {
        mMessages = messages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.mesaj_sag_layout;

        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        MesajModel message = mMessages.get(position);
        viewHolder.setMessage(message.getMesaj());
        viewHolder.setTarih(message.getTarih().toString());
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTarihView;
        private TextView mMessageView;

        public ViewHolder(View itemView) {
            super(itemView);

            mMessageView = (TextView) itemView.findViewById(R.id.mesaj);
            mTarihView = (TextView) itemView.findViewById(R.id.saat);
        }

        public void setTarih(String tarih) {
            if (null == mTarihView) return;
            mTarihView.setText(tarih);
        }

        public void setMessage(String message) {
            if (null == mMessageView) return;
            mMessageView.setText(message);
        }
    }
}
