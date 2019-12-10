package com.example.shopmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.shopmall.bean.GoodsBean;
import java.util.ArrayList;

public class GoodsInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<GoodsBean> list_goods;

    public GoodsInfoAdapter(Context context, ArrayList<GoodsBean> list_goods) {
        this.context = context;
        this.list_goods = list_goods;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GoodsInfoViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_goods_info, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,int position) {
        GoodsInfoViewHolder goodsInfoViewHolder = (GoodsInfoViewHolder) holder;
        goodsInfoViewHolder.setData(list_goods,position);
    }

    @Override
    public int getItemCount() {
        return list_goods.size();
    }

    class GoodsInfoViewHolder extends RecyclerView.ViewHolder {

        private WebView wb_figure_goods_info;
        private TextView tv_goods_name;
        private TextView tv_cover_price;
        private WebView wb_atguigu_goods_info;

        public GoodsInfoViewHolder(@NonNull View itemView) {
            super(itemView);

            wb_figure_goods_info = itemView.findViewById(R.id.wb_figure_goods_info);
            tv_goods_name = itemView.findViewById(R.id.tv_goods_name);
            tv_cover_price = itemView.findViewById(R.id.tv_cover_price);
            wb_atguigu_goods_info = itemView.findViewById(R.id.wb_atguigu_goods_info);
        }

        private void setData(final ArrayList<GoodsBean> list_goods, final int position) {
            //图片WebView
            wb_figure_goods_info.loadUrl(Constant.BASE_URL_IMAGE + list_goods.get(position).getFigure());

            wb_figure_goods_info.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    view.loadUrl(list_goods.get(position).getCover_price());
                    return true;
                }
            });

            tv_goods_name.setText(list_goods.get(position).getName());

            tv_cover_price.setText("￥" + list_goods.get(position).getCover_price());

            //详情WebView
            wb_atguigu_goods_info.loadUrl("http://www.atguigu.com");

            wb_atguigu_goods_info.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    view.loadUrl(list_goods.get(position).getCover_price());
                    return true;
                }
            });

        }
    }

}
