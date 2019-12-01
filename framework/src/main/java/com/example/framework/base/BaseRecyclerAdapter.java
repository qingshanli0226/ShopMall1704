package com.example.framework.base;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {



    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
