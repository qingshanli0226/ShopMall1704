package com.example.shopmall.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.shopmall.R;
import com.example.step.OrmUtils;
import com.example.step.RunView;
import com.example.step.ShopStepBean;
import com.example.step.StepArcView;
import com.example.step.StepManager;

import java.util.ArrayList;
import java.util.List;

public class IntegralActivity extends BaseActivity {

    TitleBar tb_integral;
    TextView intergral_Step,integral;
    RunView runView;
    StepArcView stepArcView;
    RecyclerView History_recyclerView;
    Spinner spinner;

   ArrayAdapter<String> stringArrayAdapter;
   TextView tvHistory;

    @Override
    protected int setLayout() {
        return R.layout.activity_integral;
    }

    @Override
    public void initView() {
        tb_integral = findViewById(R.id.tb_integral);
        intergral_Step=findViewById(R.id.Integral_step);
        integral=findViewById(R.id.Integral_integral);
        runView=findViewById(R.id.runView);
        stepArcView=findViewById(R.id.StepView);
        History_recyclerView=findViewById(R.id.history_RecyclerView);
        spinner=findViewById(R.id.Integral_spinner);
        tvHistory=findViewById(R.id.find_History);
    }

    @SuppressLint({"NewApi", "ResourceAsColor", "ResourceType"})
    @Override
    public void initData() {



        tb_integral.setBackgroundColor(getResources().getInteger(R.color.colorBlue));
        tb_integral.setCenterText("我的积分",18, Color.WHITE);

        tb_integral.setLeftImg(R.drawable.left);

        tb_integral.setTitleClickLisner(new TitleBar.TitleClickLisner() {
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



        tvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntegralActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
        //当前步数和积分
        List<ShopStepBean> queryAll = OrmUtils.getQueryAll(ShopStepBean.class);
        intergral_Step.setText(queryAll.get(queryAll.size()-1).getCurrent_step()+"");
        String current_step = queryAll.get(queryAll.size() - 1).getCurrent_step();
        int i = Integer.parseInt(current_step);
        stepArcView.setCurrentCount(10000,i);

//        int i1 = (int) i / 100;
//        integral.setText(i1+"");


        //设置历史记录
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        History_recyclerView.setLayoutManager(linearLayoutManager);


        //获取日期
        List<ShopStepBean> queryAll1 = OrmUtils.getQueryAll(ShopStepBean.class);
        final List<String> mlistDate=new ArrayList<>();
        for (int j=0;j<queryAll1.size();j++){
            String date = queryAll1.get(j).getDate();
            mlistDate.add(date);
        }

        //下拉框
       stringArrayAdapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mlistDate);
        spinner.setAdapter(stringArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int postion, long l) {
                String item = stringArrayAdapter.getItem(postion);
                for (int i=0;i<mlistDate.size();i++){
                    if(item.equals(mlistDate.get(i))){
                        List<ShopStepBean> day = OrmUtils.getQueryByWhere(ShopStepBean.class, "day", new String[]{item});
                        for (int q=0;q<day.size();q++){
                            stepArcView.setCurrentCount(10000,Integer.parseInt(day.get(q).getCurrent_step()));
                            integral.setText(Integer.parseInt(day.get(q).getCurrent_step())/100+"");
                        }
                    }
                }
                adapterView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
              adapterView.setVisibility(View.VISIBLE);
            }
        });

        //实时获取步数
        StepManager.getInstance().registerListener(new StepManager.StepManagerListener() {
            @Override
            public void onStepChange(int count) {
                intergral_Step.setText(count+"");
                stepArcView.setCurrentCount(10000,count);
//                List<ShopStepBean> stepHistory = StepManager.getInstance().getStepHistory();
//                StepHistoryAdapter stepHistoryAdapter = new StepHistoryAdapter(stepHistory,count);
//                History_recyclerView.setAdapter(stepHistoryAdapter);




            }

            @Override
            public void onIntegral(int intgal) {

              integral.setText(intgal+"");
            }
        });







    }
}
