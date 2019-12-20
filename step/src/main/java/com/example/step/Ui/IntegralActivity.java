package com.example.step.Ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.common.OrmUtils;
import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.bean.ShopStepBean;
import com.example.step.R;
import com.example.step.CustomView.RunView;
import com.example.framework.bean.ShopStepBean;
import com.example.step.CustomView.StepArcView;
import com.example.framework.manager.StepManager;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;


public class IntegralActivity extends BaseActivity {

    TitleBar tb_integral;
    TextView intergral_Step,integral;
    RunView runView;
    StepArcView step_ArcView;
    RecyclerView History_recyclerView;

   TextView tvHistory;

   boolean isFirst=false;
    SharedPreferences preferences;
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
        step_ArcView=findViewById(R.id.StepView);
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


        SharedPreferences is = getSharedPreferences("is", Context.MODE_PRIVATE);
        boolean first = is.getBoolean("first", false);
        if(first){
            is.edit().putBoolean("first",true).commit();
        }else{
            //      当前步数和积分
            List<ShopStepBean> queryAll = OrmUtils.getQueryAll(ShopStepBean.class);
            intergral_Step.setText(queryAll.get(queryAll.size()-1).getCurrent_step()+"");
            integral.setText(queryAll.get(queryAll.size()-1).getIntegral()+"");
            String current_step = queryAll.get(queryAll.size() - 1).getCurrent_step();
            int i = Integer.parseInt(current_step);
            step_ArcView.setCurrentCount(10000,i);
        }







        //实时获取步数
        StepManager.getInstance().registerListener(new StepManager.StepManagerListener() {
            @Override
            public void onStepChange(int count) {

                intergral_Step.setText(count+"");
                step_ArcView.setCurrentCount(10000,count);

            }

            @Override
            public void onIntegral(int intgal) {

              integral.setText(intgal+"");
            }
        });








    }




    @Override
    protected void onRestart() {
        super.onRestart();
                   List<ShopStepBean> queryAll = OrmUtils.getQueryAll(ShopStepBean.class);
                   intergral_Step.setText(queryAll.get(queryAll.size()-1).getCurrent_step()+"");
                   String current_step = queryAll.get(queryAll.size() -1).getCurrent_step();
                    int i = Integer.parseInt(current_step);
                    step_ArcView.setCurrentCount(10000,i);


    }
}
