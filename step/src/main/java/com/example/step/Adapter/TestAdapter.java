package com.example.step.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framework.bean.ShopStepBean;
import com.example.framework.bean.ShopStepTimeRealBean;
import com.example.step.R;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.MyHolder>  {

    private List<ShopStepTimeRealBean> shopStepTimeRealBeans;

    public TestAdapter(List<ShopStepTimeRealBean> threeList) {
        this.shopStepTimeRealBeans=threeList;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_adapter_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.day.setText(shopStepTimeRealBeans.get(position).getDate());
        holder.time.setText(shopStepTimeRealBeans.get(position).getTime());
        holder.count.setText(shopStepTimeRealBeans.get(position).getCurrentStep()+"步");
    }

    @Override
    public int getItemCount() {
        return shopStepTimeRealBeans.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView day,count,time;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            day=itemView.findViewById(R.id.step_item_day);
            count=itemView.findViewById(R.id.step_item_count);
            time=itemView.findViewById(R.id.step_item_time);
        }
    }
}
