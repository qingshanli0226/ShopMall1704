package com.example.administrator.shaomall.activity;

import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.transition.Slide;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.shaomall.R;
import com.example.commen.view.KeyWordFramLayout;
import com.example.commen.view.SearchEditText;
import com.shaomall.framework.base.BaseActivity;

import java.util.Random;

public class SearchActivity extends BaseActivity implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        if (v instanceof TextView) {
            String keyword = ((TextView) v).getText().toString().trim();
            searchEdit.setText(keyword);
            searchEdit.setSelection(keyword.length());
        }
        if(v.getId()==R.id.search_button&&searchEdit.getText().toString().length()!=0){
            toast("你搜索了 "+searchEdit.getText().toString(), false);
        }

        if (v.getId()==R.id.search_iv_back){
            animOutActivity(this);
        }
    }
    private static final int FEEDKEY_START = 1;
    private ImageView back_arrow;
    private ImageView search_button;
    private ImageView search_back;
    private Animation shakeAnim;
    private SearchEditText searchEdit;
    private KeyWordFramLayout keywordsFlow;
    private int STATE = 1;
    //页面滚动的数据源
    private static String[] keywords = new String[]{"弗拉基米尔", "希维尔", "蒙多",
            "茂凯", "潘森", "波比", "拉克丝", "索拉卡", "娑娜", "伊泽瑞尔", "费德提克", " 雷克顿",
            "古拉加斯", "卡萨丁", "迦娜", "奥莉安娜", "嘉文四世", " 莫德凯撒", " 崔丝塔娜", "布兰德",
            "卡尔玛", "塔里克", "莫甘娜", "凯南", " 兰博", "斯维因", "卡尔萨斯"};

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
        shakeAnim = AnimationUtils.loadAnimation(this,R.anim.popshow_anim);
        keywordsFlow = findViewById(R.id.keywordsflow);
        keywordsFlow.setDuration(1000l);
        keywordsFlow.setOnItemClickListener(this);
        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setAnimation(shakeAnim);
        searchEdit = findViewById(R.id.search_view);
        search_button= findViewById(R.id.search_button);
        search_button.setOnClickListener(this);
        search_back = findViewById(R.id.search_iv_back);
        search_back.setOnClickListener(this);
        feedKeywordsFlow(keywordsFlow, keywords);
        keywordsFlow.go2Show(KeyWordFramLayout.ANIMATION_IN);
        handler.sendEmptyMessageDelayed(FEEDKEY_START, 5000);
    }

    @Override
    protected void initData() {

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
    protected void onDestroy() {
        super.onDestroy();
        back_arrow.clearAnimation();
        handler.removeMessages(FEEDKEY_START);
        STATE = 0;
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