package com.example.shopmall.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.TitleBar;
import com.example.shopmall.R;
import com.example.shopmall.adapter.StepHistoryAdapter;
import com.example.step.ShopStepBean;
import com.example.step.StepManager;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView History_recyclerView;
    TitleBar titleBar;
    StepHistoryAdapter stepHistoryAdapter;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        History_recyclerView=findViewById(R.id.history_RecyclerView);
        titleBar=findViewById(R.id.tb_history);

        titleBar.setBackgroundColor(getResources().getInteger(R.color.colorBlue));
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


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        History_recyclerView.setLayoutManager(linearLayoutManager);

        List<ShopStepBean> stepHistory = StepManager.getInstance().getStepHistory();
        for (int i=0;i<stepHistory.size();i++){
            stepHistoryAdapter = new StepHistoryAdapter(stepHistory, Integer.parseInt(stepHistory.get(i).getCurrent_step()));
            History_recyclerView.setAdapter(stepHistoryAdapter);
        }

        StepManager.getInstance().registerListener(new StepManager.StepManagerListener() {
            @Override
            public void onStepChange(int count) {
                List<ShopStepBean> stepHistory = StepManager.getInstance().getStepHistory();
                stepHistoryAdapter = new StepHistoryAdapter(stepHistory,count);
                History_recyclerView.setAdapter(stepHistoryAdapter);
            }

            @Override
            public void onIntegral(int intgal) {

            }
        });

    }
}
