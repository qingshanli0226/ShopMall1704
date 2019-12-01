package com.example.framework.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framework.R;

import java.util.ArrayList;

public class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private int layoutId;
    private ArrayList<T> datelist;

    public BaseRecyclerAdapter(int layoutId, ArrayList<T> datelist) {
        this.layoutId = layoutId;
        this.datelist = datelist;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.page_loading,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
