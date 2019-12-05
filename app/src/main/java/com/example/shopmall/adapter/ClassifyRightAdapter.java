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
    //常用分类
    private List<ClassifyBean.ResultBean.ChildBean> child;
    //热卖商品列表的数据
    private List<ClassifyBean.ResultBean.HotProductListBean> hot_product_list;
    //热卖
    public static final int HOT = 0;
    //普通的
    public static final int ORDINARY = 1;

    //当前的类型
    public int currentType;

    private final LayoutInflater mLayoutInflater;

    public ClassifyRightAdapter(Context mContext, List<ClassifyBean.ResultBean> result) {
        this.mContext = mContext;

        mLayoutInflater = LayoutInflater.from(mContext);

        if (result != null && result.size() > 0) {
            child = result.get(0).getChild();
            hot_product_list = result.get(0).getHot_product_list();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HOT) {
            return new HotViewHolder(mLayoutInflater.inflate(R.layout.item_classify_right, null), mContext);
        } else {
            return new OrdinaryViewHolder(mLayoutInflater.inflate(R.layout.item_ordinary_right, null), mContext);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == HOT) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(hot_product_list);
        } else {
            OrdinaryViewHolder ordinaryViewHolder = (OrdinaryViewHolder) holder;
            ordinaryViewHolder.setData(child.get(position - 1), position - 1);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == HOT) {
            currentType = HOT;
        } else {
            currentType = ORDINARY;
        }
        return currentType;
    }

    @Override
    public int getItemCount() {
        return child.size() + 1;
    }

    class OrdinaryViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private ImageView iv_ordinary_right;
        private TextView tv_ordinary_right;
        private LinearLayout ll_root;

        public OrdinaryViewHolder(View itemView, final Context mContext) {
            super(itemView);
            this.mContext = mContext;
            iv_ordinary_right = (ImageView) itemView.findViewById(R.id.iv_ordinary_right);
            tv_ordinary_right = (TextView) itemView.findViewById(R.id.tv_ordinary_right);
            ll_root = (LinearLayout) itemView.findViewById(R.id.ll_root);


        }

        public void setData(ClassifyBean.ResultBean.ChildBean childBean, final int position) {
            //加载图片
            Glide.with(mContext).load(Constant.BASE_URL_IMAGE +childBean.getPic()).into(iv_ordinary_right);
            //设置名称
            tv_ordinary_right.setText(childBean.getName());

            ll_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "posotion" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    class HotViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rv_classify_right;
        private LinearLayout linear;
        private Context mContext;

        public HotViewHolder(View itemView, Context mContext) {
            super(itemView);
            this.mContext = mContext;
            rv_classify_right = itemView.findViewById(R.id.rv_classify_right);
            rv_classify_right.setLayoutManager(new GridLayoutManager(mContext,3));

        }

        public void setData(final List<ClassifyBean.ResultBean.HotProductListBean> hot_product_list) {

            ClassifyRecyclerRightAdapter classifyRecyclerRightAdapter = new ClassifyRecyclerRightAdapter(mContext, (ArrayList<ClassifyBean.ResultBean.HotProductListBean>) hot_product_list);
            rv_classify_right.setAdapter(classifyRecyclerRightAdapter);

            for (int i = 0; i < hot_product_list.size(); i++) {

                //点击事件
//                myLinear.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int i = (int) myLinear.getTag();
//
//                        String cover_price = hot_product_list.get(i).getCover_price();
//                        String name = hot_product_list.get(i).getName();
//                        String figure = hot_product_list.get(i).getFigure();
//                        String product_id = hot_product_list.get(i).getProduct_id();
//                        GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
//
//                        Intent intent = new Intent(mContext, GoodsInfoActivity.class);
//                        intent.putExtra("goods_bean", goodsBean);
//                        mContext.startActivity(intent);
//                        // Toast.makeText(mContext, "position" + i, Toast.LENGTH_SHORT).show();
//                    }
//                });
            }

        }
    }


}
