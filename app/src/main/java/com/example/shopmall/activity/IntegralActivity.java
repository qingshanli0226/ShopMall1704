package com.example.shopmall.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.shopmall.R;
import com.example.shopmall.adapter.StepHistoryAdapter;
import com.example.step.RunView;
import com.example.step.ShopStepBean;
import com.example.step.StepArcView;
import com.example.step.StepManager;

import java.util.List;

public class IntegralActivity extends BaseActivity {

    TitleBar tb_integral;
    TextView intergral_Step,integral;
    RunView runView;
    StepArcView stepArcView;
    RecyclerView History_recyclerView;
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
    }

    @SuppressLint("NewApi")
    @Override
    public void initData() {



        tb_integral.setBackgroundColor(Color.RED);
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





        stepArcView.setCurrentCount(8000,4000);
        StepManager.getInstance().registerListener(new StepManager.StepManagerListener() {
            @Override
            public void onStepChange(int count) {
                intergral_Step.setText(count+"");
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        History_recyclerView.setLayoutManager(linearLayoutManager);

        List<ShopStepBean> stepHistory = StepManager.getInstance().getStepHistory();
        StepHistoryAdapter stepHistoryAdapter = new StepHistoryAdapter(stepHistory);
        History_recyclerView.setAdapter(stepHistoryAdapter);


        int gal = StepManager.getInstance().getGal();
        integral.setText(gal+"");


    }
}
