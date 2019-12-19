package com.example.remindsteporgan;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.commen.util.PageUtil;
import com.example.remindsteporgan.Base.Bean;
import com.example.remindsteporgan.DIYView.MyPassometView;
import com.example.remindsteporgan.Util.ScreenBroadcastListener;
import com.example.remindsteporgan.Util.ScreenManager;
import com.example.view.demogreendao.BeanDao;
import com.example.view.demogreendao.DaoMaster;
import com.example.view.demogreendao.DaoSession;
import com.shaomall.framework.base.BaseActivity;
import com.shaomall.framework.manager.PointManager;

import java.util.Calendar;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class RemindActivity extends BaseActivity implements SensorEventListener {
    private TextView tv1;
    private MyPassometView viewPassometView;
    private RelativeLayout review;
    private TextView historySteps;
    PageUtil pageUtil;

    //TODO 获取当前系统的时间
    Calendar calendar = Calendar.getInstance();
    //TODO 这个是获取天数
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    float X;

    BeanDao beanDao;

    //TODO 加的步数
    float plus = 0;


    //TODO 定义的数据库
    DaoSession dao;
    //TODO SP存储
    SharedPreferences sp;
    //TODO 这个是"我的"页面里面传过来的用户名方便数据库存储
    private String username;


    @Override
    protected int setLayoutId() {
        return R.layout.remindactivity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void initView() {
        viewPassometView = (MyPassometView) findViewById(R.id.view_passomet_view);
        review = (RelativeLayout) findViewById(R.id.review);
        tv1 = (TextView) this.findViewById(R.id.tv1);
        historySteps = (TextView) findViewById(R.id.historySteps);
        TextView historySteps = (TextView) findViewById(R.id.historySteps);
        sp = getSharedPreferences("ssh", 0);

        Intent intent=getIntent();
        Bundle extras = intent.getExtras();
        username = extras.getString("username");

        //TODO 实例化数据库
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "ssh");
        SQLiteDatabase writableDatabase = openHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(writableDatabase);
        dao = daoMaster.newSession();
        beanDao = dao.getBeanDao();

        //TODO 加载动画
        pageUtil=new PageUtil(this);


        //TODO 传感器
        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // 计步统计
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        assert sm != null;
        sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),
                SensorManager.SENSOR_DELAY_NORMAL);

    }


    @Override
    protected void initData() {
        pageUtil.setReview(review);
        historySteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toClass(HistoryActivity.class);
            }
        });
    }


    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {

            //TODo 获取到系统里的步数
            X = event.values[0];
            //TODO 如果是新的一天就把X的值给y然后减去让他归零
            long y = sp.getLong("y", 0);
            Log.d("SSSH:", y + "");
            if (y == 0) {
                //TODO 第一次运行让他等于系统计步器的数量然后可以减去本身让这一天的值等于0
                SharedPreferences.Editor edit = sp.edit();
                edit.putLong("y", (long) X);
                edit.apply();
                y = (long) X;
            } else {
                //TODO 判断天数是否改变了
                int daychange = sp.getInt("daychange", 0);
                //TODO 还是同一天没有归零
                if (daychange == 0) {
                    //TODO 说明是第一次运行

                    SharedPreferences.Editor edit = sp.edit();
                    edit.putInt("daychange", day);
                    edit.apply();
                } else {
                    //TODO 不是第一次运行了
                    if (daychange == day) {
                        //TODO 还是同一天
                        //TODO step代表和上一次运行相差了多少步
                        long step = sp.getLong("step", 0);
                        //TODO 这个是算出程序没有运行期间走了多少步
                        plus=X-step;
                    } else {
                        //TODO 不是同一天了
                        daychange = day;
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putLong("y", 0);
                        edit.putLong("step", 0);
                        edit.apply();
                    }
                }
            }

            //TODO 这里判断是哪一个用户然后分别显示不同的步数


            long stepCount = (long) (X - y + plus);
            tv1.setText("今天走了" + stepCount + "步");
            viewPassometView.setText((int) stepCount);
            setStepCount2Point(stepCount);

        }
    }

    /**
     * 步数换算积分
     *
     * @param stepCount
     */
    public void setStepCount2Point(Long stepCount) {
        int point = (int) Math.floor(stepCount * 0.1); //步数换算积分
        PointManager.getInstance().setPointNum(point);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onDestroy() {
        super.onDestroy();

        //TODO 获取当前系统的时间
        Calendar calendar = Calendar.getInstance();
        //TODO 这个是获取小时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        //TODO 这个是获取天
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //TODO greendao添加语句
        beanDao.insert(new Bean((long) (plus), day + ""));
        SharedPreferences sp = getSharedPreferences("ssh", 0);
        SharedPreferences.Editor edit = sp.edit();
        //TODO 退出前提交一个退出前的步数方便下次打开做计算期间相差多少步

        edit.putLong("step", (long) X);
        edit.putString("type", "0");
        edit.apply();
    }

    private void Keep_alice() {
        final ScreenManager screenManager = ScreenManager.getInstance(this);
        ScreenBroadcastListener listener = new ScreenBroadcastListener(this);
        listener.registerListener(new ScreenBroadcastListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                screenManager.finishActivity();
            }

            @Override
            public void onScreenOff() {
                screenManager.startActivity();
            }
        });
    }
}
