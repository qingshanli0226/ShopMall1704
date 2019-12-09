package com.example.point;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;

public class StepIsSupport {
    private SensorManager sensorManager;
    private Sensor countSensor;
    //获取当前手机是否支持计步
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public boolean isSupportStepCountSensor(Context context) {
        // 获取传感器管理器的实例
        sensorManager = (SensorManager) context
                .getSystemService(context.SENSOR_SERVICE);

        countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor detectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        return countSensor != null || detectorSensor != null;
    }
}
