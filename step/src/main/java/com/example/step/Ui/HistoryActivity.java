package com.example.step.Ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.bean.HourBean;
import com.example.framework.bean.MessageBean;
import com.example.framework.bean.ShopStepTimeRealBean;
import com.example.step.Adapter.StepHistoryAdapter;
import com.example.step.Adapter.StepHistoryHourAdapter;
import com.example.step.R;
import com.example.framework.bean.ShopStepBean;
import com.example.framework.manager.StepManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HistoryActivity extends BaseActivity {

    private RecyclerView History_recyclerView;
    private TitleBar titleBar;
    private StepHistoryAdapter stepHistoryAdapter;
    private Spinner historySpinner;

    private List<ShopStepBean> shopStepBeanList=new ArrayList<>();

    private List<ShopStepBean> weekList=new ArrayList<>();
    private List<ShopStepBean> monthList=new ArrayList<>();
    private List<ShopStepBean> allList=new ArrayList<>();
    private List<HourBean>threeList=new ArrayList<>();
    @Override
    protected int setLayout() {
        return R.layout.activity_history;
    }

    @SuppressLint("ResourceType")
    @Override
    public void initView() {
        History_recyclerView=findViewById(R.id.history_RecyclerView);
        titleBar=findViewById(R.id.tb_history);
        historySpinner=findViewById(R.id.history_Spinner);

        titleBar.setBackgroundColor(getResources().getInteger(R.color.color2));
        titleBar.setCenterText("历史记录",18, Color.WHITE);

        titleBar.setLeftImg(R.drawable.left);

        titleBar.setTitleClickLisner(new TitleBar.TitleClickLisner() {
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



        //建立数据源
        String[] stringArray = getResources().getStringArray(R.array.date);

         ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stringArray);
         stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //下拉框
        historySpinner.setAdapter(stringArrayAdapter);
        historySpinner.setSelection(1);
        historySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int postion, long l) {
                String[] stringArray1 = getResources().getStringArray(R.array.date);
                String currentDate = stringArray1[postion];

                TextView tv=(TextView) view;
                tv.setTextColor(getResources().getColor(R.color.center_text_color));
                tv.setTextSize(16.0f);
                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                switch (currentDate){
                    case "三小时之内":
                        ThreeHourStep();

                        break;
                    case "今天":
                        TodayStep();
                        break;
                    case "一周之内":
                        WeekStep();
                        break;
                    case "一月之内":
                        MonthStep();
                        break;
                    case "所有":
                        AllStep();
                        break;
                }
                adapterView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                adapterView.setVisibility(View.VISIBLE);
            }
        });






        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        History_recyclerView.setLayoutManager(linearLayoutManager);
        List<ShopStepBean> stepHistory = StepManager.getInstance().getStepHistory();
        for (int i=0;i<stepHistory.size();i++){
            shopStepBeanList.add(stepHistory.get(i));
            stepHistoryAdapter = new StepHistoryAdapter(shopStepBeanList, Integer.parseInt(stepHistory.get(i).getCurrent_step()));
            History_recyclerView.setAdapter(stepHistoryAdapter);
        }
       //实时获取步数
        StepManager.getInstance().registerListener(new StepManager.StepManagerListener() {
            @Override
            public void onStepChange(int count) {
                historySpinner.setSelection(1);
                List<ShopStepBean> stepHistory = StepManager.getInstance().getStepHistory();
                for (int i=0;i<stepHistory.size();i++){
                stepHistoryAdapter = new StepHistoryAdapter(shopStepBeanList,count);
                History_recyclerView.setAdapter(stepHistoryAdapter);
                }
            }

            @Override
            public void onIntegral(int intgal) {

            }
        });

    }

    //所有记录
    private void AllStep() {
        List<ShopStepBean> stepHistory = StepManager.getInstance().getStepHistory();
        monthList.clear();
        weekList.clear();

        for (int i=0;i<stepHistory.size();i++){
            allList.add(shopStepBeanList.get(i));
            stepHistoryAdapter = new StepHistoryAdapter(allList, Integer.parseInt(stepHistory.get(i).getCurrent_step()));
            History_recyclerView.setAdapter(stepHistoryAdapter);
        }
        stepHistoryAdapter.notifyDataSetChanged();
    }

    //一月之内
    private void MonthStep() {
        List<ShopStepBean> shopStepTimeRealBeans = StepManager.getInstance().getStepHistory();
        Calendar instance = Calendar.getInstance();
        int NowMonth = instance.get(Calendar.MONTH )+1;
        for (int m=0;m<shopStepTimeRealBeans.size();m++){
            String date = shopStepTimeRealBeans.get(m).getDate();
            String[] split = date.split("-");
            //获取每月的第一天和最后一天
            int firstDayMonth = StepManager.getInstance().getFirstDayMonth(2);
            int lastDayMonth = StepManager.getInstance().getLastDayMonth(2);
            //是本月
            if(NowMonth==Integer.parseInt(split[1])){
                //在本月的日期中
                if(Integer.parseInt(split[2])>=firstDayMonth && Integer.parseInt(split[2])<=lastDayMonth){

                    threeList.clear();
                    weekList.clear();
                    allList.clear();
                    monthList.add(shopStepTimeRealBeans.get(m));
                    stepHistoryAdapter = new StepHistoryAdapter(monthList, Integer.parseInt(shopStepTimeRealBeans.get(m).getCurrent_step()));
                    History_recyclerView.setAdapter(stepHistoryAdapter);
                    History_recyclerView.scrollToPosition(monthList.size()-1);
                }{
                    weekList.clear();
                    allList.clear();
                    stepHistoryAdapter.notifyDataSetChanged();
                }
            }else {
                weekList.clear();
                allList.clear();
                stepHistoryAdapter.notifyDataSetChanged();
            }

        }
    }


    //只显示一周
    private void WeekStep() {
        List<ShopStepBean> real = StepManager.getInstance().getStepHistory();
        for (int w=0;w<real.size();w++){
            String date = real.get(w).getDate();
            List<String> weekDay = StepManager.getInstance().getWeekDay();
            for (int i=0;i<weekDay.size();i++){
                if(date.equals(weekDay.get(i))){
                    monthList.clear();
                    allList.clear();
                    threeList.clear();

                    Log.e("day",weekDay.get(i));
                    weekList.add(real.get(w));
                    stepHistoryAdapter = new StepHistoryAdapter(weekList, Integer.parseInt(real.get(w).getCurrent_step()));
                    History_recyclerView.setAdapter(stepHistoryAdapter);
                    History_recyclerView.scrollToPosition(weekList.size()-1);
                    stepHistoryAdapter.notifyDataSetChanged();
                }

            }

        }

    }
    //三小时之内
    private void ThreeHourStep() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        List<HourBean> real = StepManager.getInstance().findHour();
//        List<HourBean>threeList=new ArrayList<>();
        for (int i=0;i<real.size();i++) {
            String realTime = real.get(i).getTime();
            String[] split = realTime.split(":");
            //之前小时
            int beforeHour = Integer.parseInt(split[0]);
            int beforeMinute = Integer.parseInt(split[1]);
            //当前小时
            int thrreHour = calendar.get(Calendar.HOUR_OF_DAY) - 3;
            //如果是在三小时之内显示数据
            if (beforeHour >= thrreHour && beforeHour <= hour) {
                boolean currentTimeRange = StepManager.getInstance().isCurrentTimeRange(beforeHour, beforeMinute, hour, minute);
                if (currentTimeRange == true) {
                    Log.e("##",real.get(i).getCurrentStep()+"");
                    HourBean hourBean = new HourBean(real.get(i).getTime(), real.get(i).getDate(), real.get(i).getCurrentStep());
                    threeList.add(hourBean);
                    StepHistoryHourAdapter stepHistoryHourAdapter = new StepHistoryHourAdapter();
                    stepHistoryHourAdapter.reFresh(threeList);
                    //滑动到最后一个
                    History_recyclerView.scrollToPosition(threeList.size()-1);
                    History_recyclerView.setAdapter(stepHistoryHourAdapter);
                }
            }

        }

        }


        //获取今天数据
    private void TodayStep() {

        List<MessageBean> messDate = StepManager.getInstance().getMessDate();
        Log.e("MEss",messDate.toString());
        List<ShopStepBean> stepHistory = StepManager.getInstance().getStepHistory();
        List<ShopStepBean> todayList=new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        for (int i=0;i<stepHistory.size();i++){
            String date = stepHistory.get(i).getDate();
            String[] split = date.split("-");

            if(Integer.parseInt(split[1])==month){

                if(Integer.parseInt(split[2])==day){
                todayList.add(stepHistory.get(i)) ;
                stepHistoryAdapter = new StepHistoryAdapter(todayList, Integer.parseInt(stepHistory.get(i).getCurrent_step()));
                History_recyclerView.setAdapter(stepHistoryAdapter);
                }else{
                    threeList.clear();
                    allList.clear();
                    weekList.clear();
                    monthList.clear();
                    stepHistoryAdapter.notifyDataSetChanged();
                }
            }else{
                threeList.clear();
                allList.clear();
                weekList.clear();
                monthList.clear();
                stepHistoryAdapter.notifyDataSetChanged();
            }
        }

    }


    public void clearAll(){
        monthList.clear();
        weekList.clear();
        allList.clear();
    }



}
