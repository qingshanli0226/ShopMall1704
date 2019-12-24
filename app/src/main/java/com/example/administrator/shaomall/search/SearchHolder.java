package com.example.administrator.shaomall.search;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.shaomall.R;
import com.example.administrator.shaomall.app.ShaoHuaApplication;
import com.example.net.AppNetConfig;
import com.shaomall.framework.base.BaseHolder;
import com.shaomall.framework.bean.SearchBean;

import org.w3c.dom.Text;

public class SearchHolder extends BaseHolder<SearchBean.HotProductListBean> {
    private ImageView searchItemPicture;
    private TextView searchItemName;
    private TextView searchItemPrice;
    @Override
    protected View initView() {
        View view = View.inflate(ShaoHuaApplication.context, R.layout.item_search_info, null);
        initView(view);
        return view;
    }

    @Override
    protected void refreshData() {
        //设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(6);
//通过RequestOptions扩展功能
        RequestOptions options=RequestOptions.bitmapTransform(roundedCorners);

        Glide.with(ShaoHuaApplication.context).load(AppNetConfig.BASE_URl_IMAGE+getDatas().getFigure()).apply(options).into(searchItemPicture);
        searchItemName.setText(getDatas().getName());
        searchItemPrice.setText("￥"+getDatas().getCover_price());
    }

    private void initView(View view) {
        searchItemPicture =  view.findViewById(R.id.search_item_picture);
        searchItemName =  view.findViewById(R.id.search_item_name);
        searchItemPrice = view.findViewById(R.id.search_item_price);
        searchItemName.setTextColor(Color.BLACK);
    }
}
