package com.example.point.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buy.databeans.StepBean;
import com.example.framework.base.BaseActivity;
import com.example.point.R;
import com.example.point.adpter.StepItemAdpter;
import com.example.point.database.StepData;

import java.util.ArrayList;

public class HistoryActivity extends BaseActivity {

    private ImageView iv_left;
    private TextView physical;
    private ImageView iv_right;
    private RecyclerView history_re;
    private TextView recently;
    private ArrayList<StepBean> stepList;
    private StepData stepData;
    private StepItemAdpter stepItemAdpter;//展示数据的适配器
    @Override
    public void init() {
        iv_left = findViewById(R.id.iv_left);
        physical = findViewById(R.id.physical);
        iv_right = findViewById(R.id.iv_right);
        history_re = findViewById(R.id.history_re);
        recently = (TextView) findViewById(R.id.recently);
        stepList=new ArrayList<>();
        stepData=new StepData(this);
    }

    @Override
    public void initDate() {
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryActivity.this, StepActivity.class);
                startActivity(intent);
                finish();
            }
        });
        physical.setText("历史步数");

        history_re.setLayoutManager(new LinearLayoutManager(this));
        SQLiteDatabase database = stepData.getWritableDatabase();
      //  database.execSQL("delete  from step");
        //查询数据库
        Cursor step = database.query("step", null, null, null, null, null, null);
        int b = step.getCount();
        if (b!=0){
            history_re.setVisibility(View.VISIBLE);
            recently.setVisibility(View.GONE);
            while (step.moveToNext()){
                String stepString = step.getString(step.getColumnIndex("curr_date"));
                int number = step.getInt(step.getColumnIndex("number"));
                StepBean bean = new StepBean(stepString,number);
                stepList.add(bean);
            }
            stepItemAdpter = new StepItemAdpter(stepList);
            history_re.setAdapter(stepItemAdpter);
        }else {
            //如果数据库中没有日期数据  我们就让列表隐藏 将展示没有历史记录的控件显示
            history_re.setVisibility(View.GONE);
            recently.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.history_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void initView() {

    }
}
