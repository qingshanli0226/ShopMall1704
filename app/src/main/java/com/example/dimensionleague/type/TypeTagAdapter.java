package com.example.dimensionleague.type;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dimensionleague.R;

import java.util.List;

class TypeTagAdapter extends BaseAdapter {

    private String[] titles = new String[]{"小裙子", "上衣", "下装", "外套", "配件", "包包", "装扮", "居家宅品", "办公文具", "数码周边", "游戏专区"};
    private Context mContext;
    private int mSelect = 0;

    public TypeTagAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return titles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(mContext, R.layout.type_tag_left,null);
            viewHolder = new ViewHolder();
            viewHolder.tv_title = convertView.findViewById(R.id.type_left_tv);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_title.setText(titles[position]);

        if (mSelect == position){
            //选中项背景
            convertView.setBackgroundResource(R.drawable.background_which);
            viewHolder.tv_title.setTextColor(Color.BLACK);
            viewHolder.tv_title.setTextSize(15);
        }else {
            //其他项背景
            convertView.setBackgroundResource(R.drawable.background_gray);
            viewHolder.tv_title.setTextColor(Color.GRAY);
            viewHolder.tv_title.setTextSize(12);
        }

        return convertView;
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
