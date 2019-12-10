package com.example.step.Ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
import com.example.step.Adapter.StepHistoryAdapter;
import com.example.step.R;
import com.example.common.ShopStepBean;
import com.example.framework.manager.StepManager;

import java.util.List;

public class HistoryActivity extends BaseActivity {

    private RecyclerView History_recyclerView;
    private TitleBar titleBar;
    private StepHistoryAdapter stepHistoryAdapter;
    private Spinner historySpinner;
    private String[] dateNames=new String[]{"昨天","当天","三天之内","一周之内","一个月之内"};

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
//                String item = stringArrayAdapter.getItem(postion);

//                for (int i=0;i<mlistDate.size();i++){
//                    if(item.equals(mlistDate.get(i))){
//                        List<ShopStepBean> day = OrmUtils.getQueryByWhere(ShopStepBean.class, "day", new String[]{item});
//                        for (int q=0;q<day.size();q++){
//                            stepArcView.setCurrentCount(10000,Integer.parseInt(day.get(q).getCurrent_step()));
//                            integral.setText(Integer.parseInt(day.get(q).getCurrent_step())/100+"");
//                        }
//                    }
//                }
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
