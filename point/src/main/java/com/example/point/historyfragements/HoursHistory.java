package com.example.point.historyfragements;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.TextView;

import com.example.framework.base.BaseFragment;
import com.example.framework.bean.HourBean;
import com.example.framework.bean.StepBean;
import com.example.framework.manager.DaoManager;
import com.example.point.R;
import com.example.point.view.MiuiWeatherView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HoursHistory extends BaseFragment {
    private MiuiWeatherView hour_muiview;
    private TextView hour_mileage;
    private TextView hour_calorie;
    private List<HourBean> beans;
    @Override
    public int getLayoutId() {
        return R.layout.hour_fragements;
    }

    @Override
    public void init(View view) {
        hour_muiview = view.findViewById(R.id.hour_muiview);
        hour_mileage = view.findViewById(R.id.hour_mileage);
        hour_calorie = view.findViewById(R.id.hour_calorie);
    }

    @Override
    public void initDate() {

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                beans = DaoManager.Companion.getInstance(getActivity()).loadHourBean();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (beans.size() != 0) {
                            List<StepBean> stepBeans=new ArrayList<>();
                            for (int i = 0; i <beans.size() ; i++) {
                                StepBean addbean = new StepBean();
                                addbean.setCurr_date(beans.get(i).getHours());
                                addbean.setStep(beans.get(i).getStep());
                                stepBeans.add(addbean);
                            }
                            Collections.reverse(stepBeans);
                            hour_muiview.setData(stepBeans);
                            if (stepBeans.size() != 0) {
                                int step = stepBeans.get(0).getStep();
                                String tv_mileage = step + "";
                                tv_mileage = formatToSepara(tv_mileage) + "公里";
                                Spannable sp = new SpannableString(tv_mileage);
                                sp.setSpan(new AbsoluteSizeSpan(15, true), tv_mileage.length() - 2, tv_mileage.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                                hour_mileage.setText(sp);
                                String tv_calorie = step/80 + "";
                                tv_calorie = formatcalorie(tv_calorie) + "千卡";
                                Spannable sp1 = new SpannableString(tv_calorie);
                                sp1.setSpan(new AbsoluteSizeSpan(15, true), tv_calorie.length() - 2, tv_calorie.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                                hour_calorie.setText(sp1);
                            }
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