package com.example.point.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.framework.base.BaseActivity;
import com.example.point.R;
import com.example.point.StepIsSupport;
import com.example.point.adpter.StepItemAdpter;
import com.example.point.service.StepBean;
import com.example.point.stepmanager.DaoManager;

import java.util.Calendar;
import java.util.List;

public class HistoryActivity extends BaseActivity {

    private ImageView iv_left;
    private TextView physical;
    private ImageView iv_right;
    private RecyclerView history_re;
    private TextView recently;
    private StepItemAdpter stepItemAdpter;//展示数据的适配器
    private TextView start;
    private TextView stop;
    private DatePickerDialog dateDialog;
    private int year, monthOfYear, dayOfMonth;
    private List<StepBean> beans;
    private Spinner history_spinner;

    @Override
    public void init() {
        iv_left = findViewById(R.id.iv_left);
        physical = findViewById(R.id.physical);
        iv_right = findViewById(R.id.iv_right);
        history_re = findViewById(R.id.history_re);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        recently = (TextView) findViewById(R.id.recently);
        // 通过Calendar对象来获取年、月、日、时、分的信息
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(calendar.YEAR);
        monthOfYear = calendar.get(calendar.MONTH);
        dayOfMonth = calendar.get(calendar.DAY_OF_MONTH);
        history_spinner = findViewById(R.id.history_spinner);
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
        getSQdata();
        //开始日期
        start.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    //为空的话便不能移到第二个edittext
                    if (start.getText().toString().isEmpty()) {
                        Toast.makeText(HistoryActivity.this, "输入不能为空!", Toast.LENGTH_SHORT).show();
                    } else {
                        //光标移到stop输入框
                        stop.requestFocus();
                    }

                    return true;
                }
                return false;
            }
        });
        //停止日期
        stop.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    //为空的话便不能
                    if (stop.getText().toString().isEmpty()) {
                        Toast.makeText(HistoryActivity.this, "输入不能为空!", Toast.LENGTH_SHORT).show();
                    } else {
                        getSQdataArea(start.getText().toString(), stop.getText().toString());

                    }

                    return true;
                }
                return false;
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

        history_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               if (i==1){
                    start.setHint("开始日期");
                    stop.setHint("结束日期");
                }
                else if (i==2){
                    start.setHint("开始小时");
                    stop.setHint("结束小时");
                }
                else if (i==3){
                    start.setHint("开始分钟");
                    stop.setHint("结束分钟");                }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void getSQdata() {
        //支持计步的话就查找历史记录-否则就什么也不做
        if (new StepIsSupport().isSupportStepCountSensor(this)) {
            beans = new DaoManager(this).loadStepBean();
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
            beans = new DaoManager(this).areaStepBean(start, stop);
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

    private void initView() {
        history_spinner = (Spinner) findViewById(R.id.history_spinner);
    }
}