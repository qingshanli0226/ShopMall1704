package com.example.shopmall.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.shopmall.bean.ClassifyBean;

import java.util.List;

/**
 * ClassifyLeft
 */
public class ClassifyLeftAdapter extends RecyclerView.Adapter<ClassifyLeftAdapter.ViewHolder> {

    private Context context;

    public ClassifyLeftAdapter(Context context) {
        this.context = context;
    }

    private String[] titles = new String[]{"小裙子", "上衣", "下装", "外套", "配件", "包包", "装扮", "居家宅品", "办公文具", "数码周边", "游戏专区"};

    //选中项
    private int mSelect = 0;


    @NonNull
    @Override
    public ClassifyLeftAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_classify_left_type, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassifyLeftAdapter.ViewHolder holder, final int position) {
        holder.tv_title_classify_left.setText(titles[position]);

        if (mSelect == position){
            //选中项背景
            holder.itemView.setBackgroundResource(R.drawable.type_item_background_selector);
            holder.tv_title_classify_left.setTextColor(Color.parseColor("#fd3f3f"));
        }else {
            //其他项背景
            holder.itemView.setBackgroundResource(R.drawable.bg2);
            holder.tv_title_classify_left.setTextColor(Color.parseColor("#323437"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeliest.getLikeliest(position);
            }
        });

    }

    public void changeSelected(int position){
        if (position != mSelect){
            mSelect = position;
            notifyDataSetChanged();
        }
    }

    Likeliest likeliest;

    public interface Likeliest{
        public void getLikeliest(int position);
    }

    public void setLikeliest(Likeliest likeliest) {
        this.likeliest = likeliest;
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title_classify_left;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title_classify_left = itemView.findViewById(R.id.tv_title_classify_left);

        }
    }
}
