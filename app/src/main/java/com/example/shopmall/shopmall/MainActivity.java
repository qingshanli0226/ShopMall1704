package com.example.shopmall.shopmall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //测试开发分支
        setContentView(R.layout.activity_main);
        Log.d("LQS", "MainActivity");
    }
}
