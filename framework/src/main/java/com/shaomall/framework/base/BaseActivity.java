package com.shaomall.framework.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.commen.ShopMailError;
import com.gyf.immersionbar.ImmersionBar;
import com.shaomall.framework.R;
import com.shaomall.framework.base.view.IBaseView;
import com.shaomall.framework.manager.ActivityInstanceManager;

import java.util.List;

public abstract class BaseActivity<T> extends AppCompatActivity implements IBaseView<T> {
    private ImmersionBar immersionBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       immersionBar =  ImmersionBar.with(this);
       immersionBar
//               .statusBarDarkFont(true, 0.2f)//设置状态栏图片为深色，(如果android 6.0以下就是半透明)
               .transparentBar()
        //      .fitsSystemWindows(true)//设置这个是为了防止布局和顶部的状态栏重叠
//               .statusBarColor(setBarColor())//这里的颜色，你可以自定义。
               .init();
        setContentView(getLayoutId());

        initView();

        //activity 管理类
        ActivityInstanceManager.addActivity(this);




    }

    protected abstract int setBarColor();

    protected abstract void initView();

    public abstract int getLayoutId();







    @Override
    public void onRequestHttpDataSuccess(int requestCode, T data) {

    }

    @Override
    public void onRequestHttpDataListSuccess(int requestCode, List<T> data) {

    }

    @Override
    public void onRequestHttpDataFailed(int requestCode, ShopMailError error) {

    }

    @Override
    public void loadingPage(int code) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (immersionBar != null)
            ImmersionBar.destroy(this,null);
    }
}
