package com.example.point.historyfragements;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.TextView;

import com.example.framework.base.BaseFragment;
import com.example.framework.bean.StepBean;
import com.example.framework.manager.DaoManager;
import com.example.point.R;
import com.example.point.view.MiuiWeatherView;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DayHistory extends BaseFragment {
    private MiuiWeatherView day_muiview;
    private TextView day_mileage;
    private TextView day_calorie;
    private String CURRENT_DATE;
    private List<StepBean> stepBeans;
    private  List<StepBean> beans;
    @Override
    public int getLayoutId() {
        return R.layout.day_fragments;
    }

    @Override
    public void init(View view) {
        day_muiview = view.findViewById(R.id.day_muiview);
        day_mileage = view.findViewById(R.id.day_mileage);
        day_calorie = view.findViewById(R.id.day_calorie);
    }

    @Override
    public void initDate() {
        CURRENT_DATE = DateFormat.format("MM-dd", System.currentTimeMillis()) + "";//今日日期

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                beans = DaoManager.Companion.getInstance(getActivity()).loadStepBean();
                stepBeans = DaoManager.Companion.getInstance(getActivity()).queryStepBean(CURRENT_DATE);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (beans.size() != 0) {
                            Collections.reverse(beans);
                            day_muiview.setData(beans);
                        }
                        if (stepBeans.size() != 0) {
                            int step = stepBeans.get(0).getStep();
                            String tv_mileage = step + "";
                            tv_mileage = formatToSepara(tv_mileage) + "公里";
                            Spannable sp = new SpannableString(tv_mileage);
                            sp.setSpan(new AbsoluteSizeSpan(15, true), tv_mileage.length() - 2, tv_mileage.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                            day_mileage.setText(sp);
                            String tv_calorie = step/80 + "";
                            tv_calorie = formatcalorie(tv_calorie) + "千卡";
                            Spannable sp1 = new SpannableString(tv_calorie);
                            sp1.setSpan(new AbsoluteSizeSpan(15, true), tv_calorie.length() - 2, tv_calorie.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                            day_calorie.setText(sp1);
                        }
                    }
                });
            }
        });
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