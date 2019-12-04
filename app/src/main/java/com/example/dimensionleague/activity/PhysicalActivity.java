package com.example.dimensionleague.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.dimensionleague.R;
import com.example.framework.base.BaseActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PhysicalActivity extends BaseActivity {
    private ImageView iv_left;
    private TextView physical;
    private ImageView iv_right;
    private EditText tv_step_number;
    private CheckBox cb_remind;
    private TextView tv_remind_time;
    private Button btn_save;

    @Override
    public void init() {
        iv_left=findViewById(R.id.iv_left);
        physical=findViewById(R.id.physical);
        iv_right=findViewById(R.id.iv_right);
        tv_step_number=findViewById(R.id.tv_step_number);
        cb_remind=findViewById(R.id.cb_remind);
        tv_remind_time=findViewById(R.id.tv_remind_time);
        btn_save=findViewById(R.id.btn_save);
    }

    @Override
    public void initDate() {
        //返回计步页面
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhysicalActivity.this,StepActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //保存锻炼计划
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = tv_step_number.getText().toString();
                int i = Integer.parseInt(s);
                //存储设置的每日锻炼步数
                SharedPreferences step = getSharedPreferences("Step", MODE_PRIVATE);
                SharedPreferences.Editor edit = step.edit();
                boolean checked = cb_remind.isChecked();
                String time = tv_remind_time.getText().toString();

                edit.putBoolean("isremind",checked);
                edit.putString("time",time);
                edit.putInt("step",i);
                edit.commit();
                finish();
            }
        });
        //选择锻炼的时间
        tv_remind_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance(Locale.CHINA);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
              final   DateFormat format = new SimpleDateFormat("HH:mm");
                new TimePickerDialog(PhysicalActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int j) {
                        calendar.set(Calendar.HOUR_OF_DAY, i);
                        calendar.set(Calendar.MINUTE, j);
                        String remaintime = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
                        Date date = null;
                        try {
                            date = format.parse(remaintime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (null != date) {
                            calendar.setTime(date);
                        }
                        tv_remind_time.setText(format.format(date));
                    }
                },hour,minute,true).show();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.physical_activity;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onHttpRequestDataSuccess(int requestCode, Object data) {

    }

    @Override
    public void onHttpRequestDataListSuccess(int requestCode, List data) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initDate();

    }
}
