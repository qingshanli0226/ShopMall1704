package com.example.administrator.shaomall.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.example.administrator.shaomall.FindFragment;
import com.example.administrator.shaomall.MineFragment;
import com.example.administrator.shaomall.R;
import com.example.administrator.shaomall.ShopCarFragment;
import com.example.administrator.shaomall.TypeFragment;
import com.example.administrator.shaomall.home.HomeFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.shaomall.framework.base.BaseActivity;

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
        fragments.add(new ShopCarFragment());
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
                switchFragment(fragments.get(position));
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    protected void initData() {
        setTab();
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
