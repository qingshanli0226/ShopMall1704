package com.example.remindsteporgan.Adpter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class SQLAdp extends RecyclerView.Adapter<SQLAdp.Myholder>{

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder myholder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class Myholder extends RecyclerView.ViewHolder{

        public Myholder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
