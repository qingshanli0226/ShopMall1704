package com.example.shopmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framework.base.BaseAdapter;
import com.example.shopmall.R;
import com.example.shopmall.activity.GoodsInfoActivity;
import com.example.shopmall.bean.ClassifyBean;
import com.example.shopmall.bean.GoodsBean;

import java.util.List;

/**
 * 分类右边数据适配器
 */
public class ClassifyRightAdapter extends BaseAdapter<ClassifyBean.ResultBean,ClassifyRightAdapter.ViewHolder> {

    private Context mContext;

    public ClassifyRightAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected ViewHolder getViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    protected int getLayout(int viewType) {
        return R.layout.item_classify_right_inflate;
    }

    @Override
    protected void onBindHolder(ClassifyRightAdapter.ViewHolder holder, List<ClassifyBean.ResultBean> resultBeans, int position) {

        holder.setData(resultBeans,position);

    }

    @Override
    protected int getViewType(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView rvClassifyRightInflate;
        private RecyclerView rvOrdinaryRightInflate;

        private ViewHolder(View itemView) {
            super(itemView);
            rvClassifyRightInflate = itemView.findViewById(R.id.rv_classify_right_inflate);
            rvClassifyRightInflate.setLayoutManager(new GridLayoutManager(mContext,3));

            rvOrdinaryRightInflate = itemView.findViewById(R.id.rv_ordinary_right_inflate);
            rvOrdinaryRightInflate.setLayoutManager(new GridLayoutManager(mContext,3));

        }

        private void setData(final List<ClassifyBean.ResultBean> resultBeans, final int position) {

            //热卖的
            ClassifyRecyclerRightAdapter classifyRecyclerRightAdapter = new ClassifyRecyclerRightAdapter(mContext);
            classifyRecyclerRightAdapter.reFresh(resultBeans.get(position).getHot_product_list());
            rvClassifyRightInflate.setAdapter(classifyRecyclerRightAdapter);

            classifyRecyclerRightAdapter.setLikeliest(new ClassifyRecyclerRightAdapter.Likeliest() {
                @Override
                public void getLikeliest(int i) {
                    String cover_price = resultBeans.get(position).getHot_product_list().get(i).getCover_price();
                    String name = resultBeans.get(position).getHot_product_list().get(i).getName();
                    String figure = resultBeans.get(position).getHot_product_list().get(i).getFigure();
                    String product_id = resultBeans.get(position).getHot_product_list().get(i).getProduct_id();
                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
                    Intent intent = new Intent(mContext,GoodsInfoActivity.class);
                    intent.putExtra("goods_bean",goodsBean);
                    intent.putExtra("mainitem",1);
                    mContext.startActivity(intent);
                }
            });

            //普通的
            ClassifyOrdinaryRecyclerRightAdapter classifyOrdinaryRecyclerRightAdapter = new ClassifyOrdinaryRecyclerRightAdapter(mContext);
            classifyOrdinaryRecyclerRightAdapter.reFresh(resultBeans.get(position).getChild());
            rvOrdinaryRightInflate.setAdapter(classifyOrdinaryRecyclerRightAdapter);
        }

    }

}
