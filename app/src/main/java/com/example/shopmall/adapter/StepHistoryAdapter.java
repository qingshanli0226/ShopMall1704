package com.example.shopmall.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmall.R;
import com.example.step.ShopStepBean;

import java.util.List;

public class StepHistoryAdapter extends RecyclerView.Adapter<StepHistoryAdapter.MyHolder> {

    List<ShopStepBean> shop_step_bean;

    public StepHistoryAdapter(List<ShopStepBean> stepHistory) {
        this.shop_step_bean=stepHistory;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_adapter_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.day.setText(shop_step_bean.get(position).getDate());
        holder.count.setText(shop_step_bean.get(position).getCurrent_step());

    }

    @Override
    public int getItemCount() {
        return shop_step_bean.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        private TextView day;
        private TextView count;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            day=itemView.findViewById(R.id.step_item_day);
            count=itemView.findViewById(R.id.step_item_count);
        }
    }
}
