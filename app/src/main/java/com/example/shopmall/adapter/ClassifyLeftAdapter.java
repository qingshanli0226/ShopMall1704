package com.example.shopmall.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framework.base.BaseAdapter;
import com.example.shopmall.R;

import java.util.List;

/**
 * ClassifyLeft
 */
public class ClassifyLeftAdapter extends BaseAdapter<String,ClassifyLeftAdapter.ViewHolder> {

    //选中项
    private int mSelect = 0;

    @Override
    protected ClassifyLeftAdapter.ViewHolder getViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    protected int getLayout(int viewType) {
        return R.layout.item_classify_left_type;
    }

    @Override
    protected void onBindHolder(ClassifyLeftAdapter.ViewHolder holder, List<String> data, final int position) {

        holder.setData(data,position);

    }

    @Override
    protected int getViewType(int position) {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title_classify_left;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title_classify_left = itemView.findViewById(R.id.tv_title_classify_left);

        }

        public void setData(List<String> data, final int position) {
            tv_title_classify_left.setText(data.get(position));

            if (mSelect == position){
                //选中项背景
                itemView.setBackgroundResource(R.drawable.type_item_background_selector);
                tv_title_classify_left.setTextColor(Color.parseColor("#fd3f3f"));
            }else {
                //其他项背景
                itemView.setBackgroundResource(R.drawable.bg2);
                tv_title_classify_left.setTextColor(Color.parseColor("#323437"));
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    likeliest.getLikeliest(position);
                }
            });
        }
    }

    public void changeSelected(int position){
        if (position != mSelect){
            mSelect = position;
            notifyDataSetChanged();
        }
    }

    private Likeliest likeliest;

    public interface Likeliest{
        public void getLikeliest(int position);
    }

    public void setLikeliest(Likeliest likeliest) {
        this.likeliest = likeliest;
    }

}
