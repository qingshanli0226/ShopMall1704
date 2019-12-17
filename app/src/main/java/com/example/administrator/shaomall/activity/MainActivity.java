package com.example.administrator.shaomall.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.administrator.shaomall.FindFragment;
import com.example.administrator.shaomall.mine.MineFragment;
import com.example.administrator.shaomall.R;
import com.example.administrator.shaomall.type.TypeFragment;
import com.example.administrator.shaomall.home.HomeFragment;
import com.example.administrator.shaomall.login.LoginActivity;
import com.example.commen.util.ShopMailError;
import com.example.shoppingcart.Ui.ShoppingCartFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.shaomall.framework.base.BaseMVPActivity;
import com.shaomall.framework.bean.LoginBean;
import com.shaomall.framework.manager.ShoppingManager;
import com.shaomall.framework.manager.UserInfoManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseMVPActivity<Object> implements ShoppingManager.ShoppingNumChangeListener {
    private int[] icon = {R.drawable.main_home, R.drawable.main_type, R.drawable.cry, R.drawable.main_cart, R.drawable.main_user};
    private int[] unicon = {R.drawable.main_home_press, R.drawable.main_type_press, R.drawable.smile, R.drawable.main_cart_press, R.drawable.main_user_press};
    private String[] titles = {"首页", "分类", "发现", "购物车", "我的"};
    private FrameLayout mMainFragmentHome;
    private CommonTabLayout mMainTab;
    private ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
    private Fragment currentFragment = new Fragment();
    private List<Fragment> fragments = new ArrayList<>();
    private AutoLoginPresenter autoLoginPresenter;

    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    protected void initView() {
        mMainFragmentHome = findViewById(R.id.main_fragmentHome);
        mMainTab = findViewById(R.id.main_tab);
        fragments.add(new HomeFragment());
        fragments.add(new TypeFragment());
        fragments.add(new FindFragment());
        fragments.add(new ShoppingCartFragment());
        fragments.add(new MineFragment());

    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        //        //实现自动登录
//        //        if (autoLoginPresenter == null) {
//        //            autoLoginPresenter = new AutoLoginPresenter();
//        //            autoLoginPresenter.attachView(this);
//        //        }
//        //        if (UserInfoManager.getInstance().isLogin()) {
//        //            autoLoginPresenter.doPostHttpRequest();
//        //        }
//    }

    @Override
    public void onRequestHttpDataFailed(ShopMailError error) {
        super.onRequestHttpDataFailed(error);
        toast(error.getErrorMessage(), false);
    }

    @Override
    public void onRequestHttpDataSuccess(String message, Object data) {
        UserInfoManager.getInstance().saveUserInfo((LoginBean) data);
    }

    private void showTabSelect(int position) {
        if (position == 3) {
            //判断登录
            UserInfoManager instance = UserInfoManager.getInstance();
            if (instance.isLogin()) {
                //已经登录了
                switchFragment(fragments.get(position));

            } else {
                //还没有登录
                toClass(LoginActivity.class, position);
            }
        } else {
            switchFragment(fragments.get(position));
        }
    }

    @Override
    protected void initData() {
        setTab();
        //        mMainTab.showDot(3);//显示小红点
        //显示数量
        int shoppingNum = ShoppingManager.getInstance().getShoppingNum();
        if (shoppingNum == 0) {
            mMainTab.hideMsg(3);
        } else {
            mMainTab.showMsg(3, ShoppingManager.getInstance().getShoppingNum());
        }
        ShoppingManager.getInstance().registerShoppingNumChangeListener(this);
    }




    /**
     * 购物车数量改变
     *
     * @param num
     */
    @Override
    public void onShoppingNumChange(int num) {
        if (num == 0) {
            mMainTab.hideMsg(3); //隐藏小红点
        } else {
            mMainTab.showMsg(3, num);
        }
    }

    /**
     * 设置CommonTabLayout
     */
    private void setTab() {
        switchFragment(fragments.get(0));
        for (int i = 0; i < titles.length; i++) {
            tabEntities.add(new TabData(unicon[i], icon[i], titles[i]));
        }
        mMainTab.setTabData(tabEntities);

        mMainTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                showTabSelect(position);
            }

            @Override
            public void onTabReselect(int position) {
                showTabSelect(position);
            }
        });
    }


    /**
     * 被跳转改变
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            int index = bundle.getInt("index");
            switchFragment(fragments.get(index));
        }
    }

    /**
     * 优化fragment的切换
     * @param targetFragment
     */
    private void switchFragment(Fragment targetFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            if (currentFragment != null)
                fragmentTransaction.hide(currentFragment);
            fragmentTransaction.add(R.id.main_fragmentHome, targetFragment).commit();
        } else
            fragmentTransaction.hide(currentFragment).show(targetFragment).commit();
        currentFragment = targetFragment;
    }


    /**
     * 填充数据
     */
    class TabData implements CustomTabEntity {
        private int icon;
        private int unicon;
        private String title;

        public TabData(int icon, int unicon, String title) {
            this.icon = icon;
            this.unicon = unicon;
            this.title = title;
        }

        @Override
        public String getTabTitle() {
            return title;
        }

        @Override
        public int getTabSelectedIcon() {
            return icon;
        }

        @Override
        public int getTabUnselectedIcon() {
            return unicon;
        }
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShoppingManager.getInstance().unRegisterShoppingNumChangeListener(this);
    }
}
