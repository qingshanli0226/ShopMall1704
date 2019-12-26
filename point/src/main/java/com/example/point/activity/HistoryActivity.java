package com.example.point.activity;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.TextView;

import com.example.common.code.Constant;
import com.example.common.view.MyToolBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.bean.StepBean;
import com.example.framework.manager.DaoManager;
import com.example.point.R;
import com.example.point.view.MiuiWeatherView;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

public class HistoryActivity extends BaseActivity {

    private MyToolBar history_tool;

    private MiuiWeatherView muiview;
    private TextView history_mileage;
    private TextView history_calorie;

    @Override
    public void init() {
        history_tool = findViewById(R.id.history_tool);
        muiview = findViewById(R.id.muiview);
        history_tool.init(Constant.OTHER_STYLE);
        history_tool.setBackgroundColor(Color.rgb(34, 150, 243));
        history_mileage = findViewById(R.id.history_mileage);
        history_calorie = findViewById(R.id.history_calorie);
    }

    @Override
    public void initDate() {
        history_tool.getOther_title().setText("历史记录");
        //返回计步页
        history_tool.getOther_back().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity();
            }
        });

        List<StepBean> beans = DaoManager.Companion.getInstance(this).loadStepBean();
        if (beans.size() != 0) {
            Collections.reverse(beans);

            muiview.setData(beans);
        }
        String CURRENT_DATE = DateFormat.format("MM-dd", System.currentTimeMillis()) + "";//今日日期
        List<StepBean> stepBeans = DaoManager.Companion.getInstance(this).queryStepBean(CURRENT_DATE);
        if (stepBeans.size() != 0) {
            int step = stepBeans.get(0).getStep();
            String tv_mileage = step + "";
            tv_mileage = formatToSepara(tv_mileage) + "公里";
            Spannable sp = new SpannableString(tv_mileage);
            sp.setSpan(new AbsoluteSizeSpan(15, true), tv_mileage.length() - 2, tv_mileage.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            history_mileage.setText(sp);


            String tv_calorie = step + "";
            tv_calorie = formatToSepara(tv_calorie) + "千卡";
            Spannable sp1 = new SpannableString(tv_calorie);
            sp1.setSpan(new AbsoluteSizeSpan(15, true), tv_calorie.length() - 2, tv_calorie.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            history_calorie.setText(sp1);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.history_activity;
    }

    public String formatToSepara(String data) {
        try {
            double value = Double.parseDouble(data);
            DecimalFormat df = new DecimalFormat("#,##");
            return df.format(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public String formatcalorie(String data) {
        try {
            double value = Double.parseDouble(data);
            DecimalFormat df = new DecimalFormat("#,#");
            return df.format(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}