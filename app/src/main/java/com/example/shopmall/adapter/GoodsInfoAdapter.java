package com.example.shopmall.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framework.base.BaseAdapter;
import com.example.net.Constant;
import com.example.shopmall.MyApplication;
import com.example.shopmall.R;
import com.example.shopmall.bean.GoodsBean;
import java.util.List;

/**
 * Goods适配器
 */
public class GoodsInfoAdapter extends BaseAdapter<GoodsBean,GoodsInfoAdapter.ViewHolder> {

    private Context context;

    public GoodsInfoAdapter(Context context) {
        this.context = context;
    }

    private WebView wbFigureGoodsInfo;
    private WebView wbAtguiguGoodsInfo;

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

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvGoodsName;
        private TextView tvCoverPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            wbFigureGoodsInfo = itemView.findViewById(R.id.wb_figure_goods_info);
            tvGoodsName = itemView.findViewById(R.id.tv_goods_name);
            tvCoverPrice = itemView.findViewById(R.id.tv_cover_price);
            wbAtguiguGoodsInfo = itemView.findViewById(R.id.wb_atguigu_goods_info);
        }

        @SuppressLint("SetTextI18n")
        private void setData(final List<GoodsBean> goodsBeans, final int position) {
            //webview优化
            WebSettings settings = wbFigureGoodsInfo.getSettings();
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);

            settings.setDomStorageEnabled(true);

            settings.setDatabaseEnabled(true);
            final String dbPath = context.getDir("db", Context.MODE_PRIVATE).getPath();
            settings.setDatabasePath(dbPath);

            settings.setAppCacheEnabled(true);
            final String cachePath = context.getDir("cache", Context.MODE_PRIVATE).getPath();
            settings.setAppCachePath(cachePath);
            settings.setAppCacheMaxSize(5*1024*1024);

            settings.setJavaScriptEnabled(true);
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



            WebSettings webSettings = wbAtguiguGoodsInfo.getSettings();
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

            webSettings.setDomStorageEnabled(true);

            webSettings.setDatabaseEnabled(true);
            final String db = MyApplication.getContext().getDir("db", Context.MODE_PRIVATE).getPath();
            webSettings.setDatabasePath(db);

            webSettings.setAppCacheEnabled(true);
            final String cache = MyApplication.getContext().getDir("cache", Context.MODE_PRIVATE).getPath();
            webSettings.setAppCachePath(cache);
            webSettings.setAppCacheMaxSize(5*1024*1024);

            webSettings.setJavaScriptEnabled(true);
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

    public void getWebView(){
        if (wbFigureGoodsInfo != null){
            ViewParent parent = wbFigureGoodsInfo.getParent();
            if (parent != null){
                ((ViewGroup)parent).removeView(wbFigureGoodsInfo);
            }
            wbFigureGoodsInfo.stopLoading();
            wbFigureGoodsInfo.getSettings().setJavaScriptEnabled(false);
            wbFigureGoodsInfo.clearHistory();
            wbFigureGoodsInfo.clearView();
            wbFigureGoodsInfo.removeAllViews();
            wbFigureGoodsInfo.destroy();
            wbFigureGoodsInfo = null;
        }
        if (wbAtguiguGoodsInfo != null){
            ViewParent parent = wbAtguiguGoodsInfo.getParent();
            if (parent != null){
                ((ViewGroup)parent).removeView(wbAtguiguGoodsInfo);
            }
            wbAtguiguGoodsInfo.stopLoading();
            wbAtguiguGoodsInfo.getSettings().setJavaScriptEnabled(false);
            wbAtguiguGoodsInfo.clearHistory();
            wbAtguiguGoodsInfo.clearView();
            wbAtguiguGoodsInfo.removeAllViews();
            wbAtguiguGoodsInfo.destroy();
            wbAtguiguGoodsInfo = null;
        }
    }

}
