package com.example.administrator.shaomall.search;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.shaomall.R;
import com.example.commen.util.PinYinUtil;
import com.example.commen.util.ShopMailError;
import com.example.commen.view.ClearEditText;
import com.example.commen.view.FlowLayout;
import com.example.commen.view.KeyWordFramLayout;
import com.example.commen.view.SearchEditText;
import com.shaomall.framework.base.BaseMVPActivity;
import com.shaomall.framework.bean.SearchBean;
import com.shaomall.framework.manager.SearchManager;

import java.util.List;
import java.util.Random;

import co.lujun.androidtagview.TagContainerLayout;

public class SearchActivity extends BaseMVPActivity<SearchBean> implements View.OnClickListener {
    private SearchPresenter searchPresenter;
    private static final int FEEDKEY_START = 1;
    private ImageView back_arrow;
    private ImageButton search_button;
    private ImageButton search_back;
    private Animation shakeAnim;
    private ClearEditText searchEdit;
    private KeyWordFramLayout keywordsFlow;
    private FlowLayout search_flow;
    private ImageButton search_clear;
    private TextView search_history;
    private int STATE = 1;

    @Override
    public void onClick(View v) {
        if (v instanceof TextView) {
            String keyword = ((TextView) v).getText().toString().trim();
            searchEdit.setText(keyword);
            searchEdit.setSelection(keyword.length());
        }
        if (v.getId() == R.id.search_button && searchEdit.getText().toString().length() != 0) {
            Toast.makeText(SearchActivity.this, "你搜索了 " + searchEdit.getText().toString(), Toast.LENGTH_SHORT).show();
            String editString = searchEdit.getText().toString();
            boolean data = SearchManager.getInstance().hasData(editString);
            if (!data) {
                SearchManager.getInstance().insertData(editString);
                addTag(editString);
            }

            String pingYin = PinYinUtil.getPingYin(editString);
            searchPresenter = new SearchPresenter(pingYin);
            searchPresenter.attachView(this);
            searchPresenter.doGetHttpRequest();
            Bundle bundle = new Bundle();
            bundle.putString("search_name", searchEdit.getText().toString());
            toClass(SearchInfoActivity.class, bundle);
        }

        if (v.getId() == R.id.search_iv_back) {
            animOutActivity();
        }

        if (v.getId() == R.id.search_clear) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("确定删除全部历史记录?");
            // 设置取消按钮
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            // 设置确定按钮
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    search_flow.removeAllViews();
                    SearchManager.getInstance().deleteData();
                    search_clear.setVisibility(View.INVISIBLE);
                    search_history.setVisibility(View.INVISIBLE);
                }
            });
            // 和Toast一样 最后一定要show 出来
            builder.show();
        }
    }

    //页面滚动的数据源
    private static String[] keywords = new String[]{"小裙子", "裤子", "鞋子",
            "上衣", "下装", "裤子", "外套", "配件", "包包", "装扮", "居家宅品", " 办公",
            "数码周边", "游戏", "文具", "手办", "耳机", " 画板", " 假发", "茶杯",
            "笔记本", "毛巾", "零食", "公仔", " 桌游", "挂件", "海报"};

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case FEEDKEY_START:
                    keywordsFlow.rubKeywords();
                    feedKeywordsFlow(keywordsFlow, keywords);
                    keywordsFlow.go2Show(KeyWordFramLayout.ANIMATION_OUT);
                    sendEmptyMessageDelayed(FEEDKEY_START, 5000);
                    break;
            }
        }
    };

    @Override
    protected int setLayoutId() {
        return R.layout.activity_search;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initView() {

        shakeAnim = AnimationUtils.loadAnimation(this, R.anim.popshow_anim);
        keywordsFlow = findViewById(R.id.keywordsflow);
        keywordsFlow.setDuration(1000l);
        keywordsFlow.setOnItemClickListener(this);
        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setAnimation(shakeAnim);
        searchEdit = findViewById(R.id.search_view);
        search_clear = findViewById(R.id.search_clear);
        search_clear.setOnClickListener(this);
        search_button = findViewById(R.id.search_button);
        search_flow = findViewById(R.id.search_flow);
        search_button.setOnClickListener(this);
        search_back = findViewById(R.id.search_iv_back);
        search_back.setOnClickListener(this);
        search_history = findViewById(R.id.search_history);
        feedKeywordsFlow(keywordsFlow, keywords);
        keywordsFlow.go2Show(KeyWordFramLayout.ANIMATION_IN);
        handler.sendEmptyMessageDelayed(FEEDKEY_START, 5000);
    }

    @Override
    protected void initData() {
        List<String> list = SearchManager.getInstance().selectAll();
        Log.i("TAG", "initData: " + list.size());
        if (list != null){
            addTags(list);

        }


    }

    //添加单条记录
    private void addTag(String tag) {
        search_history.setVisibility(View.VISIBLE);
        search_clear.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 5, 10, 5);
        TextView tv = new TextView(this);
        tv.setPadding(28, 10, 28, 10);
        tv.setText(tag);
        tv.setMaxEms(10);
        tv.setSingleLine();
        tv.setBackgroundResource(R.drawable.tag_text_background);
        tv.setLayoutParams(layoutParams);
        search_flow.addView(tv, layoutParams);
    }

    //添加多条记录
    private void addTags(List<String> list) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 5, 10, 5);
        if (search_flow != null) {
            search_flow.removeAllViews();
        }
        for (int i = 0; i < list.size(); i++) {
            TextView tv = new TextView(this);
            tv.setPadding(28, 10, 28, 10);
            tv.setText(list.get(i));
            tv.setMaxEms(10);
            tv.setSingleLine();
            tv.setBackgroundResource(R.drawable.tag_text_background);
            tv.setLayoutParams(layoutParams);
            search_flow.addView(tv, layoutParams);
        }
    }

    private static void feedKeywordsFlow(KeyWordFramLayout keywordsFlow, String[] arr) {
        Random random = new Random();
        for (int i = 0; i < KeyWordFramLayout.MAX; i++) {
            int ran = random.nextInt(arr.length);
            String tmp = arr[ran];
            keywordsFlow.feedKeyword(tmp);
        }
    }


    @Override
    public void onRequestHttpDataListSuccess(String message, List data) {
        List<SearchBean> searchBeans = data;
        Log.i("TAG", "onRequestHttpDataListSuccess: " + searchBeans.get(0).getName());
    }

    @Override
    public void onRequestHttpDataFailed(ShopMailError error) {
        super.onRequestHttpDataFailed(error);
        toast(error.getErrorMessage(), false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        back_arrow.clearAnimation();
        handler.removeMessages(FEEDKEY_START);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeMessages(FEEDKEY_START);
        STATE = 0;
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeMessages(FEEDKEY_START);
        STATE = 0;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (STATE == 0) {
            keywordsFlow.rubKeywords();
            handler.sendEmptyMessageDelayed(FEEDKEY_START, 3000);
        }
    }

}