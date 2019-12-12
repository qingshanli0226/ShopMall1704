package com.example.buy.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.buy.R;
import com.example.common.NumberAddSubView;
import com.example.framework.base.BaseAdapter;

import java.util.List;
import java.util.Map;

public class MyShoppingBasketAdapter extends BaseAdapter<Map<String, String>, MyShoppingBasketAdapter.ViewHolder> {


    private double allcount = 0;
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

    public void refresh2(List<Map<String, String>> data, int position, double allcount) {
        reFreshOneLine(data, position);
        this.allcount = allcount;
    }

    public void setAllcount(double allcount) {
        this.allcount = allcount;
    }

    @Override
    protected ViewHolder getViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    protected int getLayout(int viewType) {
        return R.layout.item_shop_cart;
    }

    @Override
    protected void onBindHolder(final ViewHolder holder, final List<Map<String, String>> data, final int position) {
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
//                    Log.e("####",allcount+"");
                    allcount += count;
//                    Log.e("####",count+"/"+allcount);
                    Message message = Message.obtain();
                    message.what = 100;
                    message.arg2 = position;
                    message.obj = "true " + x + " " + allcount;
                    handler.sendMessage(message);

                    checkedcount++;
                } else {
                    int count = x * Integer.parseInt(hashMap.get("price"));
                    allcount -= count;

                    Message message = Message.obtain();
                    message.what = 100;
                    message.arg2 = position;
                    message.obj = "false " + x + " " + allcount;
                    handler.sendMessage(message);

                    checkedcount--;
                }
                Log.e("####", checkedcount + "");
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

    @Override
    protected int getViewType(int position) {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        ImageView iv_img = itemView.findViewById(com.example.buy.R.id.iv_gov);
        TextView tv_title = itemView.findViewById(com.example.buy.R.id.tv_desc_gov);
        NumberAddSubView numberAddSubView = itemView.findViewById(com.example.buy.R.id.numberAddSubView);
        CheckBox cb_gov = itemView.findViewById(com.example.buy.R.id.cb_gov);

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
