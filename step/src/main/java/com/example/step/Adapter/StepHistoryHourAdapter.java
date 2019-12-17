package com.example.step.Adapter;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framework.base.BaseAdapter;
import com.example.framework.bean.HourBean;
import com.example.framework.bean.ShopStepTimeRealBean;
import com.example.step.R;

import java.util.List;

public class StepHistoryHourAdapter extends BaseAdapter<HourBean, StepHistoryHourAdapter.HourMyHolder>{


    @Override
    protected HourMyHolder getViewHolder(View view, int viewType) {
        return new HourMyHolder(view);
    }

    @Override
    protected int getLayout(int viewType) {
        return R.layout.step_adapter_item;
    }


    @Override
    protected void onBindHolder(HourMyHolder holder, List<HourBean> shopStepTimeRealBeans, int type) {
        holder.day.setText(shopStepTimeRealBeans.get(type).getDate());
        holder.time.setText(shopStepTimeRealBeans.get(type).getTime());
        holder.count.setText(shopStepTimeRealBeans.get(type).getCurrentStep()+"步");
    }



    @Override
    protected int getViewType(int position) {
        return 0;
    }

    class HourMyHolder extends RecyclerView.ViewHolder{

        TextView day,count,time;
        public HourMyHolder(@NonNull View itemView) {
            super(itemView);
            day=itemView.findViewById(R.id.step_item_day);
            count=itemView.findViewById(R.id.step_item_count);
            time=itemView.findViewById(R.id.step_item_time);
        }
    }
}
