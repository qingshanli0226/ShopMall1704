package com.example.buy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PayActivity extends AppCompatActivity implements View.OnClickListener {

    private Button payBut;

    int money;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        initView();
        //http://49.233.93.155:8080  updateMoney  money=1333
    }

    private void initView() {
        payBut = findViewById(R.id.payBut);
        payBut.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=getIntent();
        money= intent.getIntExtra("money",0);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.payBut) {
            //检验,然后支付
            verifyMoney();
        }
    }

    //发起更新现金请求
    private void verifyMoney(){
        //{"code":"200","message":"请求成功","result":"6666666"}
        //如果没钱则提示用户,他是个穷人,待支付
        orderCancel();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        orderCancel();
    }

    //支付取消,更改订单为待支付订单
    private void orderCancel(){

    }
}
