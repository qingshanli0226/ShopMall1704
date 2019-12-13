package com.example.shopmall.adapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framework.base.BaseAdapter;
import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.shopmall.bean.GoodsBean;
import java.util.List;

/**
 * Goods适配器
 */
public class GoodsInfoAdapter extends BaseAdapter<GoodsBean,GoodsInfoAdapter.ViewHolder> {

    @Override
    protected GoodsInfoAdapter.ViewHolder getViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    protected int getLayout(int viewType) {
        return R.layout.item_goods_info_inflate;
    }

    @Override
    protected void onBindHolder(GoodsInfoAdapter.ViewHolder holder, List<GoodsBean> goodsBeans, int position) {
        holder.setData(goodsBeans,position);
    }

    @Override
    protected int getViewType(int position) {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private WebView wbFigureGoodsInfo;
        private TextView tvGoodsName;
        private TextView tvCoverPrice;
        private WebView wbAtguiguGoodsInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            wbFigureGoodsInfo = itemView.findViewById(R.id.wb_figure_goods_info);
            tvGoodsName = itemView.findViewById(R.id.tv_goods_name);
            tvCoverPrice = itemView.findViewById(R.id.tv_cover_price);
            wbAtguiguGoodsInfo = itemView.findViewById(R.id.wb_atguigu_goods_info);
        }

        @SuppressLint("SetTextI18n")
        private void setData(final List<GoodsBean> goodsBeans, final int position) {
            //图片WebView
            wbFigureGoodsInfo.loadUrl(Constant.BASE_URL_IMAGE + goodsBeans.get(position).getFigure());

            wbFigureGoodsInfo.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    view.loadUrl(goodsBeans.get(position).getCover_price());
                    return true;
                }
            });

            tvGoodsName.setText(goodsBeans.get(position).getName());

            tvCoverPrice.setText("￥" + goodsBeans.get(position).getCover_price());

            //详情WebView
            wbAtguiguGoodsInfo.loadUrl("http://www.atguigu.com");

            wbAtguiguGoodsInfo.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    view.loadUrl(goodsBeans.get(position).getCover_price());
                    return true;
                }
            });

        }
    }

}
