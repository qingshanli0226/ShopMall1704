package com.example.administrator.shaomall;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int[] icon = {R.drawable.main_home, R.drawable.main_type, R.drawable.cry, R.drawable.main_cart, R.drawable.main_user};
    private int[] unicon = {R.drawable.main_home_press, R.drawable.main_type_press, R.drawable.smile, R.drawable.main_cart_press, R.drawable.main_user_press};
    private String[] titles = {"首页", "分类", "发现", "购物车", "我的"};
    private FrameLayout mMainFragmentHome;
    private CommonTabLayout mMainTab;
    private ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setTab();
    }

    private void setTab() {
        for (int i = 0; i < titles.length; i++) {
            tabEntities.add(new TabData(unicon[i],icon[i],titles[i]));
        }
        mMainTab.setTabData(tabEntities);
    }


    private void initView() {
        mMainFragmentHome = findViewById(R.id.main_fragmentHome);
        mMainTab = findViewById(R.id.main_tab);
    }

    class TabData implements CustomTabEntity{
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
