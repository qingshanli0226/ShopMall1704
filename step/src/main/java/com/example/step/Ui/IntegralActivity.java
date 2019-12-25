package com.example.step.Ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.common.OrmUtils;
import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.base.IPostBaseView;
import com.example.framework.bean.ShopStepBean;
import com.example.framework.manager.UserManager;
import com.example.step.CustomView.RunView;
import com.example.step.CustomView.StepArcView;
import com.example.framework.manager.StepManager;
import com.example.step.PointBresenter;
import com.example.step.PonitBean;
import com.example.step.R;

import java.util.List;


public class IntegralActivity extends BaseActivity implements StepManager.StepManagerListener, IPostBaseView<PonitBean> {

    TitleBar tb_integral;
    TextView intergral_Step,integral;
    RunView runView;
    StepArcView step_ArcView;

   TextView tvHistory;

    PointBresenter pointBresenter;

    @Override
    protected int setLayout() {
        return R.layout.activity_integral;
    }

    @Override
    public void initView() {

        //555
        tb_integral = findViewById(R.id.tb_integral);
        intergral_Step=findViewById(R.id.Integral_step);
        integral=findViewById(R.id.Integral_integral);
        runView=findViewById(R.id.runView);
        step_ArcView=findViewById(R.id.StepView);
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
            int count=0;
            for (int i=0;i<queryAll.size();i++){
                int current_step = queryAll.get(i).getIntegral();
                count+=current_step;
            integral.setText(count+"");
            }
               intergral_Step.setText(queryAll.get(queryAll.size()-1).getCurrent_step()+"");
            String current_step = queryAll.get(queryAll.size() - 1).getCurrent_step();
            int i = Integer.parseInt(current_step);
            step_ArcView.setCurrentCount(10000,i);
        }

        //实时获取步数
        StepManager.getInstance().registerListener(this);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(pointBresenter!=null){
         pointBresenter.detachView();
        }
        StepManager.getInstance().unRegisterLisener(this);
    }

    @Override
    public void onStepChange(int count) {
        intergral_Step.setText(count+"");
        step_ArcView.setCurrentCount(10000,count);
    }

    @Override
    public void onIntegral(int intgal) {

        integral.setText(intgal+"");
        if(UserManager.getInstance().getToken()!=null){
            pointBresenter= new PointBresenter(intgal);
            pointBresenter.attachPostView(this);
            pointBresenter.getCipherTextData();
        }else{

        }

    }

    @Override
    public void onPostDataSucess(PonitBean data) {

//        Log.e("Data","Data" + data.toString());
    }

    @Override
    public void onPostDataFailed(String ErrorMsg) {

//        Log.e("Data","ErrorMsg" + ErrorMsg);
    }
}
