package com.example.administrator.shaomall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //测试
        //sas
        Intent intent=new Intent(this, com.example.remindsteporgan.MainActivity.class);

    }
}
