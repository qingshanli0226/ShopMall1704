package com.example.shopmall.activity;

import android.content.Intent;
import android.widget.Toast;

import com.example.framework.base.BaseActivity;
import com.example.framework.base.BasePresenter;
import com.example.framework.base.IBaseView;
import com.example.framework.manager.ConnectManager;
import com.example.shopmall.R;
import com.example.shopmall.bean.HomepageBean;
import com.example.shopmall.presenter.WelcomePresenter;

public class WelcomeActivity extends BaseActivity implements IBaseView<HomepageBean> {


    private BasePresenter welcomePresenter;

    @Override
    protected int setLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        welcomePresenter = new WelcomePresenter();
        welcomePresenter.attachView(this);
        welcomePresenter.getGetData();
        boolean connectStatus = ConnectManager.getInstance().getConnectStatus();
        if (connectStatus) {
//            Toast.makeText(this, "有网络连接", Toast.LENGTH_SHORT).show();
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
//                    finish();
//                }
//            },3000);
        } else {
            Toast.makeText(this, "无网络连接", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onGetDataSucess(HomepageBean data) {
    }

    @Override
    public void onPostDataSucess(HomepageBean data) {

    }

    @Override
    public void onGetDataFailed(String ErrorMsg) {
    }

    @Override
    public void onLoadingPage() {

    }

    @Override
    public void onStopLoadingPage() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        welcomePresenter.detachView();
    }
}