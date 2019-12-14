package com.example.shopmall.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.framework.base.BaseAdapter;
import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.shopmall.bean.ClassifyBean;

import java.util.List;

/**
 * 常用分类适配器
 */
public class ClassifyOrdinaryRecyclerRightAdapter extends BaseAdapter<ClassifyBean.ResultBean.ChildBean,ClassifyOrdinaryRecyclerRightAdapter.ViewHolder> {

    private Context mContext;

    public ClassifyOrdinaryRecyclerRightAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected ClassifyOrdinaryRecyclerRightAdapter.ViewHolder getViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    protected int getLayout(int viewType) {
        return R.layout.item_ordinary_right;
    }

    @Override
    protected void onBindHolder(ClassifyOrdinaryRecyclerRightAdapter.ViewHolder holder, List<ClassifyBean.ResultBean.ChildBean> child_bean, int position) {

        holder.setData(child_bean,position);

    }

    @Override
    protected int getViewType(int position) {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivOrdinaryRight;
        private TextView tvOrdinaryRight;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivOrdinaryRight = itemView.findViewById(R.id.iv_ordinary_right);
            tvOrdinaryRight = itemView.findViewById(R.id.tv_ordinary_right);

        }

        public void setData(List<ClassifyBean.ResultBean.ChildBean> child_bean, int position) {
            Glide.with(mContext).load(Constant.BASE_URL_IMAGE + child_bean.get(position).getPic()).into(ivOrdinaryRight);
            tvOrdinaryRight.setText(child_bean.get(position).getName());
        }
    }
}
