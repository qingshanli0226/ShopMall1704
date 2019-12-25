package com.example.point.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.common.code.Constant;
import com.example.common.view.MyToolBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.manager.DaoManager;
import com.example.point.R;
import com.example.point.StepIsSupport;
import com.example.point.adpter.StepItemAdpter;
import com.example.framework.bean.StepBean;


import java.util.Calendar;
import java.util.List;

public class HistoryActivity extends BaseActivity {

    private RecyclerView history_re;
    private TextView recently;
    private StepItemAdpter stepItemAdpter;//展示数据的适配器
    private TextView start;
    private TextView stop;
    private DatePickerDialog dateDialog;
    private int year, monthOfYear, dayOfMonth;
    private List<StepBean> beans;
    private Spinner history_spinner;
    private MyToolBar history_tool;
    private Button show;

    @Override
    public void init() {
        history_tool = findViewById(R.id.history_tool);
        history_tool.init(Constant.OTHER_STYLE);
        history_tool.setBackgroundColor(Color.rgb(34, 150, 243));
        history_re = findViewById(R.id.history_re);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        show = findViewById(R.id.show);
        recently = findViewById(R.id.recently);

        // 通过Calendar对象来获取年、月、日、时、分的信息
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(calendar.YEAR);
        monthOfYear = calendar.get(calendar.MONTH);
        dayOfMonth = calendar.get(calendar.DAY_OF_MONTH);
        history_spinner = findViewById(R.id.history_spinner);

    }

    @Override
    public void initDate() {
        history_tool.getOther_title().setText("历史记录");


        //返回计步页
        history_tool.getOther_back().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        history_re.setLayoutManager(new LinearLayoutManager(this));
        getSQdata();
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSQdataArea(start.getText().toString(), stop.getText().toString());
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                 * 实例化DatePickerDialog
                 */
                dateDialog = new DatePickerDialog(HistoryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        // 把获取的日期显示在文本框内，月份从0开始计数，所以要加1
                        String isOne = "" + i2;
                        //判断是天数是个位数的时候给他前面添加一个0
                        if (isOne.length() == 1) {
                            String text = (i1 + 1) + "-0" + i2;
                            start.setText(text);
                        } else {
                            String text = (i1 + 1) + "-" + i2;
                            start.setText(text);
                        }
                    }
                }, year, monthOfYear, dayOfMonth);
                dateDialog.show();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                 * 实例化DatePickerDialog
                 */
                dateDialog = new DatePickerDialog(HistoryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        // 把获取的日期显示在文本框内，月份从0开始计数，所以要加1
                        String isOne = "" + i2;
                        //判断是天数是个位数的时候给他前面添加一个0
                        if (isOne.length() == 1) {
                            String text = (i1 + 1) + "-0" + i2;
                            stop.setText(text);
                        } else {
                            String text = (i1 + 1) + "-" + i2;
                            stop.setText(text);
                        }

                    }
                }, year, monthOfYear, dayOfMonth);
                dateDialog.show();
            }
        });
        if (stepItemAdpter != null) {
            stepItemAdpter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent(HistoryActivity.this, RecordActivity.class);
                    String curr_date = beans.get(position).getCurr_date();
                    int step = beans.get(position).getStep();
                    //携带页面子数据
                    Bundle bundle = new Bundle();
                    bundle.putString("curr_date", curr_date);
                    bundle.putInt("step", step);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        history_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    start.setText("开始日期");
                    stop.setText("结束日期");
                } else if (i == 1) {
                    start.setText("开始小时");
                    stop.setText("结束小时");
                } else if (i == 2) {
                    start.setText("开始分钟");
                    stop.setText("结束分钟");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.history_activity;
    }


    public void getSQdata() {
        //支持计步的话就查找历史记录-否则就什么也不做
        if (new StepIsSupport().isSupportStepCountSensor(this)) {

            beans = DaoManager.Companion.getInstance(this).loadStepBean();

            beans = DaoManager.Companion.getInstance(this).loadStepBean();
            stepItemAdpter = new StepItemAdpter(beans);
            history_re.setAdapter(stepItemAdpter);
        } else {
            //如果数据库中没有日期数据  我们就让列表隐藏 将展示没有历史记录的控件显示
            history_re.setVisibility(View.GONE);
            recently.setVisibility(View.VISIBLE);
        }
    }

    //区间记录
    public void getSQdataArea(String start, String stop) {
        //支持计步的话就获取-否则就什么也不做
        if (new StepIsSupport().isSupportStepCountSensor(this)) {
            beans.clear();

            beans = DaoManager.Companion.getInstance(this).areaStepBean(start, stop);

            beans = DaoManager.Companion.getInstance(this).areaStepBean(start, stop);
            if (beans.size() > 0) {
                history_re.setVisibility(View.VISIBLE);
                recently.setVisibility(View.GONE);
                stepItemAdpter = new StepItemAdpter(beans);
                history_re.setAdapter(stepItemAdpter);
            } else {
                //如果数据库中没有日期数据  我们就让列表隐藏 将展示没有历史记录的控件显示
                history_re.setVisibility(View.GONE);
                recently.setVisibility(View.VISIBLE);
            }
        } else {
            //如果数据库中没有日期数据  我们就让列表隐藏 将展示没有历史记录的控件显示
            history_re.setVisibility(View.GONE);
            recently.setVisibility(View.VISIBLE);
        }
    }
}