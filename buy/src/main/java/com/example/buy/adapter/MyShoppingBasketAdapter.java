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

import java.math.BigDecimal;
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
                .into(holder.ivImg);

        holder.tvTitle.setText(hashMap.get("title"));
        holder.numberAddSubView.setPrice(hashMap.get("price"));

        holder.numberAddSubView.setOnNumberChangeListener(listener);

        holder.numberAddSubView.setValue(Integer.parseInt(hashMap.get("num")));

        holder.numberAddSubView.setPostion(position);

        String ischecked = hashMap.get("ischecked");

        if (ischecked.equals("true")) {
            holder.cbGov.setChecked(true);
            holder.numberAddSubView.setChecked(true);
        } else if (ischecked.equals("false")) {
            holder.cbGov.setChecked(false);
            holder.numberAddSubView.setChecked(false);
        }

        final int x = holder.numberAddSubView.getValue();

        holder.cbGov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.cbGov.isChecked()) {
                    double count = x * Double.parseDouble(hashMap.get("price"));
                    Log.e("####", allcount + "");
                    allcount += count;
                    Log.e("####", count + "/" + allcount);
                    Message message = Message.obtain();
                    message.what = 100;
                    message.arg2 = position;
                    message.obj = "true " + x + " " + allcount;
                    handler.sendMessage(message);

                    checkedcount++;
                } else {
                    double count = x * Double.parseDouble(hashMap.get("price"));
                    BigDecimal bigDecimal = new BigDecimal(allcount + "");
                    BigDecimal bigDecimal2 = new BigDecimal(count + "");
                    allcount = bigDecimal.subtract(bigDecimal2).doubleValue();
                    Message message = Message.obtain();
                    message.what = 100;
                    message.arg2 = position;
                    message.obj = "false " + x + " " + allcount;
                    handler.sendMessage(message);

                    checkedcount--;
                }
//                Log.e("####", checkedcount + "");
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        ImageView ivImg = itemView.findViewById(R.id.iv_buy_gov);
        TextView tvTitle = itemView.findViewById(R.id.tv_buy_descgov);
        NumberAddSubView numberAddSubView = itemView.findViewById(R.id.number_buy_addsubview);
        CheckBox cbGov = itemView.findViewById(R.id.cb_buy_gov);
    }
}
