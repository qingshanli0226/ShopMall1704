package com.example.remindsteporgan;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.remindsteporgan.Util.ScreenBroadcastListener;
import com.example.remindsteporgan.Util.ScreenManager;

public class MainActivity extends AppCompatActivity  implements SensorEventListener {
    private TextView tv1;
    private TextView tv2;
    float y=0;

    Sensor defaultSensor;
    private SensorManager sm;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        tv2 = (TextView) findViewById(R.id.tv2);
        tv1 = (TextView) findViewById(R.id.tv1);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // 计步统计
        sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER), SensorManager.SENSOR_DELAY_NORMAL);
        // 单次计步
        sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR), SensorManager.SENSOR_DELAY_NORMAL);


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_STEP_COUNTER){

            float X = event.values[0];
            if (y==0){
                y=X;
            }else {

            }
            tv1.setText("COUNTER："+(X-y));

        } else if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
            float X = event.values[0];
            tv2.setText("DECTOR："+ X );
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
