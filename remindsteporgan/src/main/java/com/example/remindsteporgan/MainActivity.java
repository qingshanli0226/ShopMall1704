package com.example.remindsteporgan;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.remindsteporgan.Base.Bean;
import com.example.remindsteporgan.Util.ScreenBroadcastListener;
import com.example.remindsteporgan.Util.ScreenManager;
import com.example.view.demogreendao.BeanDao;
import com.example.view.demogreendao.DaoMaster;
import com.example.view.demogreendao.DaoSession;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements SensorEventListener {
    private TextView tv1;
    private TextView tv2;
    float y=0;

    float plus=0;
    int daychange;
    Sensor defaultSensor;
    private SensorManager sm;
    DaoSession dao;
    SharedPreferences sp;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv1 = (TextView) findViewById(R.id.tv1);

        //实例化数据库
        DaoMaster.DevOpenHelper openHelper=new DaoMaster.DevOpenHelper(this,"ssh");
        SQLiteDatabase writableDatabase = openHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(writableDatabase);
        dao = daoMaster.newSession();
        //添加
        //daoSession.insert()

        sp=getSharedPreferences("ssh",0);
        String type = sp.getString("type", "");
        if (type.equals("")){
            //第一次运行不管
        }else {
            //已经不是第一次运行
            //step代表和上一次运行相差了多少步
            int step = sp.getInt("step", 0);
            plus=step;
        }

        //系统保活
        Keep_alice();



        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // 计步统计
        sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER), SensorManager.SENSOR_DELAY_NORMAL);
        // 单次计步
        sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR), SensorManager.SENSOR_DELAY_NORMAL);


    }

    private void Keep_alice() {
        final ScreenManager screenManager = ScreenManager.getInstance(this);
        ScreenBroadcastListener listener=new ScreenBroadcastListener(this);
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

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_STEP_COUNTER){

            float X = event.values[0];
            if (y==0){
                y=X;
            }else {

            }
            tv1.setText("COUNTER："+(X-y+plus));

            //获取当前系统的时间
            Calendar calendar=Calendar.getInstance();
            //这个是获取小时
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            //这个是获取天
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            BeanDao beanDao = dao.getBeanDao();

            List<Bean> where_num = beanDao.queryRaw("where num");
            for (Bean bean : where_num) {
                Log.d("SSHHH",bean.getNum().toString());
            }

            beanDao.insert(new Bean((long) (X-y+plus),day+""));

        } else if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
            float X = event.values[0];
            tv2.setText("DECTOR："+ X);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
