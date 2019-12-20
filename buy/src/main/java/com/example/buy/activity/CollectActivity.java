package com.example.buy.activity;

import com.example.buy.R;
import com.example.framework.base.BaseActivity;
import com.umeng.analytics.MobclickAgent;

public class CollectActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    public void init() {

    }

    @Override
    public void initDate() {
        //sp读取收藏的物品名称,然后进行网络请求,接着展示

    }
    @Override
    protected void onPause() {
        MobclickAgent.onPause(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onResume(this);
        super.onResume();
    }
}
