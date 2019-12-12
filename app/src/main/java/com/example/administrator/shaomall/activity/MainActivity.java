package com.example.administrator.shaomall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.example.administrator.shaomall.FindFragment;
import com.example.administrator.shaomall.mine.MineFragment;
import com.example.administrator.shaomall.R;
import com.example.administrator.shaomall.type.TypeFragment;
import com.example.administrator.shaomall.home.HomeFragment;
import com.example.administrator.shaomall.login.LoginActivity;
import com.example.shoppingcart.Ui.ShoppingcartActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.shaomall.framework.base.BaseActivity;
import com.shaomall.framework.manager.UserInfoManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private int[] icon = {R.drawable.main_home, R.drawable.main_type, R.drawable.cry, R.drawable.main_cart, R.drawable.main_user};
    private int[] unicon = {R.drawable.main_home_press, R.drawable.main_type_press, R.drawable.smile, R.drawable.main_cart_press, R.drawable.main_user_press};
    private String[] titles = {"首页", "分类", "发现", "购物车", "我的"};
    private FrameLayout mMainFragmentHome;
    private CommonTabLayout mMainTab;
    private ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
    private Fragment currentFragment = new Fragment();
    private List<Fragment> fragments = new ArrayList<>();

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
        fragments.add(new ShoppingcartActivity());
        fragments.add(new MineFragment());

    }

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
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            int index = bundle.getInt("index");
            switchFragment(fragments.get(index));
        }
    }

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
}
