package com.example.shopmall.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopmall.R;
import com.example.shopmall.view.NumberAddSubView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyShoppingBasketAdapter extends RecyclerView.Adapter<MyShoppingBasketAdapter.ViewHolder> {

    private List<Map<String, String>> data = new ArrayList<>();

    private int allcount = 0;
    private Context context;
    private NumberAddSubView.OnNumberChangeListener listener;

    private Handler handler;

    private int checkedcount = 0;

    public MyShoppingBasketAdapter(Context context, NumberAddSubView.OnNumberChangeListener listener, Handler handler) {
        this.context = context;
        this.listener = listener;
        this.handler = handler;
    }

    public void setCheckedcount(int checkedcount) {
        this.checkedcount = checkedcount;
    }

    public void refresh(List<Map<String, String>> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void refresh2(List<Map<String, String>> data, int position, int allcount) {
        this.data.clear();
        this.data.addAll(data);
        notifyItemChanged(position);
        this.allcount = allcount;
    }

    public void setAllcount(int allcount) {
        this.allcount = allcount;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_cart, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Map<String, String> hashMap = data.get(position);
        Glide.with(context)
                .load(hashMap.get("img"))
                .into(holder.iv_img);

        holder.tv_title.setText(hashMap.get("title"));
        holder.numberAddSubView.setPrice(hashMap.get("price"));

        holder.numberAddSubView.setOnNumberChangeListener(listener);

        holder.numberAddSubView.setValue(Integer.parseInt(hashMap.get("num")));

        holder.numberAddSubView.setPostion(position);

        String ischecked = hashMap.get("ischecked");
        if (ischecked.equals("true")) {
            holder.cb_gov.setChecked(true);
            holder.numberAddSubView.setChecked(true);
        } else if (ischecked.equals("false")) {
            holder.cb_gov.setChecked(false);
            holder.numberAddSubView.setChecked(false);
        }

        final int x = holder.numberAddSubView.getValue();

        holder.cb_gov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.cb_gov.isChecked()) {

                    int count = x * Integer.parseInt(hashMap.get("price"));
                    allcount += count;

                    Message message = Message.obtain();
                    message.what = 100;
                    message.arg1 = allcount;
                    message.arg2 = position;
                    message.obj = "true " + x;
                    handler.sendMessage(message);

                    checkedcount++;
                } else {

                    int count = x * Integer.parseInt(hashMap.get("price"));
                    Log.e("####", allcount + "");
                    allcount -= count;

                    Log.e("####", count + "/" + allcount);

                    Message message = Message.obtain();
                    message.what = 100;
                    message.arg1 = allcount;
                    message.arg2 = position;
                    message.obj = "false " + x;
                    handler.sendMessage(message);

                    checkedcount--;
                }

                Message message = Message.obtain();
                message.what = 200;
                if (checkedcount == data.size()) {
                    message.arg1 = 0;
                    handler.sendMessage(message);
                } else {
                    message.arg1 = 1;
                    handler.sendMessage(message);
                }
            }
        });

    }

    class ViewHolder extends RecyclerView.ViewHolder {


        ImageView iv_img = itemView.findViewById(R.id.iv_gov);
        TextView tv_title = itemView.findViewById(R.id.tv_desc_gov);
        NumberAddSubView numberAddSubView = itemView.findViewById(R.id.numberAddSubView);
        CheckBox cb_gov = itemView.findViewById(R.id.cb_gov);

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
