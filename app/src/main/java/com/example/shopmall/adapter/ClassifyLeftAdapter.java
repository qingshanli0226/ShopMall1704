package com.example.shopmall.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.shopmall.bean.ClassifyBean;

import java.util.List;

public class ClassifyLeftAdapter extends BaseAdapter {

    private Context mContext;

    private String[] titles = new String[]{"小裙子", "上衣", "下装", "外套", "配件", "包包", "装扮", "居家宅品", "办公文具", "数码周边", "游戏专区"};

    //选中项
    private int mSelect = 0;

    public ClassifyLeftAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int i) {
        return titles[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (view == null){
            view = View.inflate(mContext, R.layout.item_type,null);
            viewHolder = new ViewHolder();
            viewHolder.tv_title = view.findViewById(R.id.tv_title);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tv_title.setText(titles[i]);

        if (mSelect == i){
            //选中项背景
            view.setBackgroundResource(R.drawable.type_item_background_selector);
            viewHolder.tv_title.setTextColor(Color.parseColor("#fd3f3f"));
        }else {
            //其他项背景
            view.setBackgroundResource(R.drawable.bg2);
            viewHolder.tv_title.setTextColor(Color.parseColor("#323437"));
        }

        return view;
    }

    public void changeSelected(int position){
        if (position != mSelect){
            mSelect = position;
            notifyDataSetChanged();
        }
    }

    class ViewHolder{
        private TextView tv_title;
    }

}
