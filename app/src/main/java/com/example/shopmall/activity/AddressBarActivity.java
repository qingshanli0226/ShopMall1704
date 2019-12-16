package com.example.shopmall.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.shopmall.R;

public class AddressBarActivity extends BaseActivity {

    private TitleBar tbAddressBar;
    private RecyclerView rvAddressBar;
    private Button btNewReceivingAddress;

    @Override
    protected int setLayout() {
        return R.layout.activity_address_bar;
    }

    @Override
    public void initView() {
        tbAddressBar = findViewById(R.id.tb_address_bar);
        rvAddressBar = findViewById(R.id.rv_address_bar);
        btNewReceivingAddress = findViewById(R.id.bt_new_receiving_address);
    }

    @Override
    public void initData() {
        tbAddressBar.setTitleBacKGround(Color.WHITE);
        tbAddressBar.setCenterText("收货地址",18,Color.BLACK);
        tbAddressBar.setLeftImg(R.drawable.left);

        tbAddressBar.setTitleClickLisner(new TitleBar.TitleClickLisner() {
            @Override
            public void LeftClick() {
                finish();
            }

            @Override
            public void RightClick() {

            }

            @Override
            public void CenterClick() {

            }
        });

        btNewReceivingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddressBarActivity.this,LocationActivity.class));
            }
        });

    }
}