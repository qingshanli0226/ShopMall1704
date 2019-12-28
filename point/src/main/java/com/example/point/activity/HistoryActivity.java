package com.example.point.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.common.code.Constant;
import com.example.common.view.MyToolBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.bean.StepBean;
import com.example.point.R;
import com.example.point.adpter.FragementsVp;
import com.example.point.historyfragements.DayHistory;
import com.example.point.historyfragements.HoursHistory;
import com.example.point.historyfragements.MinutesHistory;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends BaseActivity {
    private MyToolBar history_tool;
    private String CURRENT_DATE;
    private List<StepBean> stepBeans;
    private List<StepBean> beans;
    private TabLayout history_tab;
    private ViewPager history_vp;
    private List<String> stringList;
    private List<Fragment> fragmentList;
    private FragementsVp fragementsVp;
    @Override
    public void init() {
        history_tool = findViewById(R.id.history_tool);
        history_tab = findViewById(R.id.history_tab);
        history_vp = findViewById(R.id.history_vp);
        history_tool.init(Constant.OTHER_STYLE);
        history_tool.setBackground(getResources().getDrawable(R.drawable.toolbar_style));
        stringList=new ArrayList<>();
        stringList.add("日期");
        stringList.add("小时");
        stringList.add("分钟");
        fragmentList=new ArrayList<>();
        fragmentList.add(new DayHistory());
        fragmentList.add(new HoursHistory());
        fragmentList.add(new MinutesHistory());
        fragementsVp=new FragementsVp(getSupportFragmentManager(),stringList,fragmentList);
    }

    @Override
    public void initDate() {
        history_tool.getOther_title().setText("历史记录");
        //返回计步页
        history_tool.getOther_back().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        history_vp.setAdapter(fragementsVp);
        history_tab.setupWithViewPager(history_vp);

    }

    @Override
    public int getLayoutId() {
        return R.layout.history_activity;
    }


}