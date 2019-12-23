package com.example.commen;

import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import static android.webkit.WebSettings.LOAD_DEFAULT;

public class WebViewConfig {
    //初始化WebView配置
    public static void initWebViewConfig(WebView webview) {
        // 设置WebView初始化尺寸，参数为百分比
        webview.setInitialScale(100);
        //设置WebView可触摸放大缩小
        webview.getSettings().setSupportZoom(false);
        webview.getSettings().setBuiltInZoomControls(false);
        //WebView双击变大，再双击后变小，当手动放大后，双击可以恢复到原始大小
        webview.getSettings().setUseWideViewPort(true);
        // 获取WebSettings对象
        WebSettings webSettings = webview.getSettings();
        // 设置WebView支持运行普通的Javascript
        webSettings.setJavaScriptEnabled(true);
        // 设置WebChromeClient，以支持运行特殊的Javascript
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
        //（默认）根据cache-control决定是否从网络上取数据。
        webSettings.setCacheMode(LOAD_DEFAULT);
        // 阻止网络图片加載
        webview.getSettings().setBlockNetworkImage(false);
    }

    //销毁资源
    public static void destroy(WebView webview) {
        webview.stopLoading(); //停止加载
        ((ViewGroup) webview.getParent()).removeView(webview); //把webview从视图中移除
        webview.removeAllViews(); //移除webview上子view
        webview.clearCache(true); //清除缓存
        webview.clearHistory(); //清除历史
        webview.destroy(); //销毁webview自身
        //Process.killProcess(Process.myPid()); //杀死WebView所在的进程
    }
}
