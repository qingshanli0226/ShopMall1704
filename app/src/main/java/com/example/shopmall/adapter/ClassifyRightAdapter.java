package com.example.shopmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmall.R;
import com.example.shopmall.activity.GoodsInfoActivity;
import com.example.shopmall.bean.ClassifyBean;
import com.example.shopmall.bean.GoodsBean;

import java.util.ArrayList;
import java.util.List;


public class ClassifyRightAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    //热卖商品列表的数据
    private List<ClassifyBean.ResultBean> result_bean;

    //当前的类型
    public int currentType;

    private final LayoutInflater mLayoutInflater;

    public ClassifyRightAdapter(Context context, List<ClassifyBean.ResultBean> result_bean) {
        this.context = context;
        this.result_bean = result_bean;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new OrdinaryViewHolder(mLayoutInflater.inflate(R.layout.item_classify_right_inflate, parent,false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            OrdinaryViewHolder ordinaryViewHolder = (OrdinaryViewHolder) holder;
            ordinaryViewHolder.setData(result_bean,position);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return result_bean.size();
    }

    class OrdinaryViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView rv_classify_right_inflate;
        private RecyclerView rv_ordinary_right_inflate;

        public OrdinaryViewHolder(View itemView) {
            super(itemView);
            rv_classify_right_inflate = itemView.findViewById(R.id.rv_classify_right_inflate);
            rv_classify_right_inflate.setLayoutManager(new GridLayoutManager(context,3));

            rv_ordinary_right_inflate = itemView.findViewById(R.id.rv_ordinary_right_inflate);
            rv_ordinary_right_inflate.setLayoutManager(new GridLayoutManager(context,3));

        }

        private void setData(final List<ClassifyBean.ResultBean> resultBeans, final int position) {

            //热卖的
            ClassifyRecyclerRightAdapter classifyRecyclerRightAdapter = new ClassifyRecyclerRightAdapter(context, (ArrayList<ClassifyBean.ResultBean.HotProductListBean>) resultBeans.get(position).getHot_product_list());
            rv_classify_right_inflate.setAdapter(classifyRecyclerRightAdapter);

            classifyRecyclerRightAdapter.setLikeliest(new ClassifyRecyclerRightAdapter.Likeliest() {
                @Override
                public void getLikeliest(int i) {
                    String cover_price = resultBeans.get(position).getHot_product_list().get(i).getCover_price();
                    String name = resultBeans.get(position).getHot_product_list().get(i).getName();
                    String figure = resultBeans.get(position).getHot_product_list().get(i).getFigure();
                    String product_id = resultBeans.get(position).getHot_product_list().get(i).getProduct_id();
                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
                    Intent intent = new Intent(context,GoodsInfoActivity.class);
                    intent.putExtra("goods_bean",goodsBean);
                    context.startActivity(intent);
                }
            });

            //普通的
            ClassifyOrdinaryRecyclerRightAdapter classifyOrdinaryRecyclerRightAdapter = new ClassifyOrdinaryRecyclerRightAdapter(context, (ArrayList<ClassifyBean.ResultBean.ChildBean>) resultBeans.get(position).getChild());
            rv_ordinary_right_inflate.setAdapter(classifyOrdinaryRecyclerRightAdapter);
        }

    }

}
