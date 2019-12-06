package com.example.shoppingcart.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class RvAdp extends RecyclerView.Adapter<RvAdp.Myhodler> {

    @NonNull
    @Override
    public Myhodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Myhodler myhodler, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class Myhodler extends RecyclerView.ViewHolder{

        public Myhodler(@NonNull View itemView) {
            super(itemView);
        }
    }
}

