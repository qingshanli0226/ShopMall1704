package com.example.step.Ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.common.OrmUtils;
import com.example.step.R;
import com.example.step.CustomView.RunView;
import com.example.common.ShopStepBean;
import com.example.step.CustomView.StepArcView;
import com.example.framework.manager.StepManager;

import java.util.ArrayList;
import java.util.List;

public class IntegralActivity extends BaseActivity {

    TitleBar tb_integral;
    TextView intergral_Step,integral;
    RunView runView;
    StepArcView stepArcView;
    RecyclerView History_recyclerView;

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
        tvHistory=findViewById(R.id.find_History);
    }

    @SuppressLint({"NewApi", "ResourceAsColor", "ResourceType"})
    @Override
    public void initData() {


        tb_integral.setBackgroundColor(getResources().getInteger(R.color.color2));
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
        //获取日期
        List<ShopStepBean> queryAll1 = OrmUtils.getQueryAll(ShopStepBean.class);
        final List<String> mlistDate=new ArrayList<>();
        for (int j=0;j<queryAll1.size();j++){
            String date = queryAll1.get(j).getDate();
            mlistDate.add(date);
        }

        //下拉框
       stringArrayAdapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mlistDate);



        //实时获取步数
        StepManager.getInstance().registerListener(new StepManager.StepManagerListener() {
            @Override
            public void onStepChange(int count) {
                intergral_Step.setText(count+"");
                stepArcView.setCurrentCount(10000,count);

            }

            @Override
            public void onIntegral(int intgal) {

              integral.setText(intgal+"");
            }
        });
    }
}
