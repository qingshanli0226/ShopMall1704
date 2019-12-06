package com.example.shopmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.shopmall.activity.GoodsInfoActivity;
import com.example.shopmall.bean.ClassifyBean;
import com.example.shopmall.bean.GoodsBean;
import com.example.shopmall.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;


public class ClassifyRightAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    //热卖商品列表的数据
    private List<ClassifyBean.ResultBean> resultBeans;
    //热卖
    public static final int HOT = 0;
    //普通的
    public static final int ORDINARY = 1;

    //当前的类型
    public int currentType;

    private final LayoutInflater mLayoutInflater;

    public ClassifyRightAdapter(Context mContext, List<ClassifyBean.ResultBean> result) {
        this.mContext = mContext;
        this.resultBeans = result;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new OrdinaryViewHolder(mLayoutInflater.inflate(R.layout.item_classify_right, parent,false), mContext);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            OrdinaryViewHolder ordinaryViewHolder = (OrdinaryViewHolder) holder;
            ordinaryViewHolder.setData(resultBeans,position);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return resultBeans.size();
    }

    class OrdinaryViewHolder extends RecyclerView.ViewHolder {
        public Context mContext;
        public RecyclerView rv_classify_right;
        public RecyclerView rv_ordinary_right;

        public OrdinaryViewHolder(View itemView, final Context mContext) {
            super(itemView);
            this.mContext = mContext;
            rv_classify_right = itemView.findViewById(R.id.rv_classify_right);
            rv_classify_right.setLayoutManager(new GridLayoutManager(mContext,3));

            rv_ordinary_right = itemView.findViewById(R.id.rv_ordinary_right);
            rv_ordinary_right.setLayoutManager(new GridLayoutManager(mContext,3));

        }

        public void setData(final List<ClassifyBean.ResultBean> resultBeans, final int position) {

            ClassifyRecyclerRightAdapter classifyRecyclerRightAdapter = new ClassifyRecyclerRightAdapter(mContext, (ArrayList<ClassifyBean.ResultBean.HotProductListBean>) resultBeans.get(position).getHot_product_list());
            rv_classify_right.setAdapter(classifyRecyclerRightAdapter);

            classifyRecyclerRightAdapter.setLinkedlist(new ClassifyRecyclerRightAdapter.Linkedlist() {
                @Override
                public void getLinkedlist(int i) {
                    String cover_price = resultBeans.get(position).getHot_product_list().get(i).getCover_price();
                    String name = resultBeans.get(position).getHot_product_list().get(i).getName();
                    String figure = resultBeans.get(position).getHot_product_list().get(i).getFigure();
                    String product_id = resultBeans.get(position).getHot_product_list().get(i).getProduct_id();
                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
                    Intent intent = new Intent(mContext,GoodsInfoActivity.class);
                    intent.putExtra("goods_bean",goodsBean);
                    mContext.startActivity(intent);
                }
            });


            ClassifyOrdinaryRecyclerRightAdapter classifyOrdinaryRecyclerRightAdapter = new ClassifyOrdinaryRecyclerRightAdapter(mContext, (ArrayList<ClassifyBean.ResultBean.ChildBean>) resultBeans.get(position).getChild());
            rv_ordinary_right.setAdapter(classifyOrdinaryRecyclerRightAdapter);
        }

    }

}
