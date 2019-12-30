package com.example.point.activity;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.common.code.Constant;
import com.example.common.view.MyToolBar;
import com.example.framework.base.BaseActivity;
import com.example.point.R;
import com.example.point.view.AnimationButton;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PhysicalActivity extends BaseActivity {

    private EditText tv_step_number;
    private CheckBox cb_remind;
    private TextView tv_remind_time;
    private AnimationButton btn_save;
    private MyToolBar physical_tool;
    private LinearLayout physical_one;
    private LinearLayout physical_two;
    private LinearLayout physical_three;

    @Override
    public void init() {
        physical_tool = (MyToolBar) findViewById(R.id.physical_tool);
        tv_step_number = findViewById(R.id.tv_step_number);
        cb_remind = findViewById(R.id.cb_remind);
        tv_remind_time = findViewById(R.id.tv_remind_time);
        btn_save = findViewById(R.id.save);
        physical_one = (LinearLayout) findViewById(R.id.physical_one);
        physical_two = (LinearLayout) findViewById(R.id.physical_two);
        physical_three = (LinearLayout) findViewById(R.id.physical_three);
    }

    @Override
    public void initDate() {
        SharedPreferences step = getSharedPreferences("Step", MODE_PRIVATE);
        tv_step_number.setText(step.getInt("step",0)+"");
        tv_remind_time.setText(step.getString("time","00:00"));

        physical_tool.init(Constant.OTHER_STYLE);
        physical_tool.getOther_title().setText("锻炼计划");

        physical_tool.setBackground(getResources().getDrawable(R.drawable.toolbar_style));
        //返回计步页
        physical_tool.getOther_back().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity();
            }
        });
        btn_save.setAnimationButtonListener(new AnimationButton.AnimationButtonListener() {
            @Override
            public void onClickListener() {
                btn_save.start();
            }

            @Override
            public void animationFinish() {

                String s = tv_step_number.getText().toString();
                try {
                    int i = Integer.parseInt(s);
                    //存储设置的每日锻炼步数
                    SharedPreferences step = getSharedPreferences("Step", MODE_PRIVATE);
                    SharedPreferences.Editor edit = step.edit();
                    boolean checked = cb_remind.isChecked();
                    String time = tv_remind_time.getText().toString();

                    edit.putBoolean("isremind", checked);
                    edit.putString("time", time);
                    edit.putInt("step", i);
                    edit.commit();
                }catch (Exception e){
                    Toast.makeText(PhysicalActivity.this, "你是要飞吗？", Toast.LENGTH_SHORT).show();
                    tv_step_number.setText(0+"");
                    btn_save.reset();
                    return;
                }
                Toast.makeText(PhysicalActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                btn_save.reset();
                finishActivity();
            }
        });

        //选择锻炼的时间
        tv_remind_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showtime();
            }
        });
        //第一个line布局
        physical_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_step_number.setText("");
                tv_step_number.requestFocus();
            }
        });
        //第二个line布局
        physical_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb_remind.isChecked()){
                    cb_remind.setChecked(false);
                }else {
                    cb_remind.setChecked(true);
                }
            }
        });
        //第三个line布局z
        physical_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showtime();
            }
        });
    }

    private void showtime() {
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        final DateFormat format = new SimpleDateFormat("HH:mm");
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
        }, hour, minute, true).show();
    }

    @Override
    public int getLayoutId() {
        return R.layout.physical_activity;
    }

}
