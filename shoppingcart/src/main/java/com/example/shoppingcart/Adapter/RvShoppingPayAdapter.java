package com.example.shoppingcart.Adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.shoppingcart.R;
import com.shaomall.framework.base.IBaseRecyclerAdapter;
import com.shaomall.framework.bean.ShoppingCartBean;

import java.util.List;

public class RvShoppingPayAdapter extends IBaseRecyclerAdapter<ShoppingCartBean> {

    public RvShoppingPayAdapter(@Nullable List<ShoppingCartBean> data) {
        super(R.layout.item_cart_order, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShoppingCartBean item) {
        helper.setText(R.id.tv_goods_name, item.getProductName());
        helper.setText(R.id.tv_goods_price, item.getProductPrice());
        helper.setText(R.id.tv_num, "x" + item.getProductNum());
        ImageView imageView = helper.getView(R.id.iv_adapter_list_pic);
        Glide.with(imageView.getContext()).load(item.getUrl()).into(imageView);
    }
}
