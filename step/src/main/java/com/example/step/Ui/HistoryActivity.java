package com.example.step.Ui;

import android.annotation.SuppressLint;
import android.graphics.Color;

import androidx.fragment.app.Fragment;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.step.Adapter.HistoryFragmentAdapter;
import com.example.step.CustomView.NoScrollViewPage;
import com.example.step.Fragment.History_MinutesFragment;
import com.example.step.Fragment.History_MonthFragment;
import com.example.step.Fragment.History_TodayFragment;
import com.example.step.Fragment.History_WeekFragment;
import com.example.step.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends BaseActivity {
    TitleBar History_titleBar;
    TabLayout History_tableLayout;
    NoScrollViewPage History_viewPager;

    List<String> tableList=new ArrayList<>();
    List<Fragment> fragmentList=new ArrayList<>();
    @Override
    protected int setLayout() {
        return R.layout.activity_testhistory;
    }

    @SuppressLint("ResourceType")
    @Override
    public void initView() {
        History_titleBar=findViewById(R.id.tb_history);
        History_tableLayout=findViewById(R.id.history_tableLayout);
        History_viewPager=findViewById(R.id.history_viewPager);
        History_titleBar.setBackgroundColor(getResources().getInteger(R.color.color2));
        History_titleBar.setCenterText("历史记录",18, Color.WHITE);

        History_titleBar.setLeftImg(R.drawable.left);

        History_titleBar.setTitleClickLisner(new TitleBar.TitleClickLisner() {
            @Override
            public void LeftClick() {
                finish();
            }

            @Override
            public void RightClick() {

            }

            @Override
            public void CenterClick() {

            }
        });



    }

    @Override
    public void initData() {

        tableList.add("三小时数据");
        tableList.add("今天");
        tableList.add("一周内");
        tableList.add("一月内");
        tableList.add("所有");


        fragmentList.add(new History_MinutesFragment());
        fragmentList.add(new History_TodayFragment());
        fragmentList.add(new History_WeekFragment());
        fragmentList.add(new History_MonthFragment());

        HistoryFragmentAdapter fragmentAdapter = new HistoryFragmentAdapter(getSupportFragmentManager(), fragmentList, tableList);
        History_viewPager.setAdapter(fragmentAdapter);

        History_tableLayout.setupWithViewPager(History_viewPager);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragmentList.clear();
    }
}
