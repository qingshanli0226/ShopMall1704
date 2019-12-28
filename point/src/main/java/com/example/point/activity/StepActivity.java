package com.example.point.activity;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import com.example.common.code.Constant;
import com.example.common.view.MyToolBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.manager.DaoManager;
import com.example.point.R;
import com.example.point.StepIsSupport;
import com.example.framework.bean.StepBean;
import com.example.point.stepmanager.StepPointManager;
import com.example.point.view.StepView;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StepActivity extends BaseActivity {
    private TextView tv_isSupport;
    private StepView stepView;
    private TextView time;
    private int i = 0;//循环的初始值
    private int v = 1;//获取当前步数
    private int stepInt;
    Handler handler=new MyHandler(this);
    private static class MyHandler extends Handler {
        private WeakReference<StepActivity> mWeakReference;

        public MyHandler(StepActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            StepActivity activity = mWeakReference.get();
            if (msg.what == 0) {
                boolean b = DateFormat.is24HourFormat(activity);
                if (b) {
                    CharSequence charSequence = DateFormat.format("dd-MM HH:mm:ss", System.currentTimeMillis());
                    activity.time.setText("时间" + charSequence);

                } else {
                    CharSequence charSequence = DateFormat.format("dd-MM hh:mm:ss aa", System.currentTimeMillis());
                    activity.time.setText("时间" + charSequence);
                }
            }
        }
    }
    private TextView tv_set;
    private TextView tv_data;
    private MyToolBar step_tool;
    private   List<StepBean> beans;
    @Override
    public void init() {
        tv_isSupport = findViewById(R.id.tv_isSupport);
        stepView = findViewById(R.id.step);
        time = findViewById(R.id.time);
        tv_set = findViewById(R.id.tv_set);
        tv_data = findViewById(R.id.tv_data);
        step_tool = findViewById(R.id.step_tool);
        step_tool.init(Constant.OTHER_STYLE);
        step_tool.getOther_title().setText("计步页");
        step_tool.setBackground(getResources().getDrawable(R.drawable.toolbar_style));
        SharedPreferences step = getSharedPreferences("Step", MODE_PRIVATE);
        stepInt = step.getInt("step", 3000);
        //返回积分页
        step_tool.getOther_back().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //跳转到锻炼页面
        tv_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(PhysicalActivity.class,null);

            }
        });

        //跳转到历史记录页面
        tv_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(HistoryActivity.class,null);
            }
        });

    }


    @Override
    public void initDate() {
        //时间的显示
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    handler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();

        //线程池
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                String CURRENT_DATE = DateFormat.format("MM-dd", System.currentTimeMillis())+"";//今日日期

                 beans = DaoManager.Companion.getInstance(StepActivity.this).queryStepBean(CURRENT_DATE);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (beans.size()!=0){
                            stepView.setCurrentCount(stepInt, beans.get(0).getStep());
                        }else {
                            stepView.setCurrentCount(stepInt,0);
                        }
                    }
                });
            }
        });
        if (new StepIsSupport().isSupportStepCountSensor(this)) {
            tv_isSupport.setText("计步中...");
            StepPointManager.getInstance(this).addGetStepListener(new StepPointManager.GetStepListener() {
                @Override
                public void onsetStep(int step) {
                    stepView.setCurrentCount(stepInt, step);
                }
            });
        } else {
            tv_isSupport.setText("该设备不支持计步");
        }
    }

    @Override
    public int getLayoutId() {

        return R.layout.step_activity;
    }
}
