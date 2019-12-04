package com.example.shopmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.base.BaseActivity;
import com.example.common.TitleBar;
import com.example.shopmall.R;

public class LoginActivity extends BaseActivity {

    TitleBar tb_login;
    Button bt_login;
    Button bt_register;

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        tb_login = findViewById(R.id.tb_login);
        bt_login = findViewById(R.id.bt_login);
        bt_register = findViewById(R.id.bt_register);
    }

    @Override
    public void initData() {
        tb_login.setBackgroundColor(Color.RED);
        tb_login.setLeftImg(R.drawable.left);
        tb_login.setCenterText("登录",18,Color.WHITE);

        tb_login.setTitleClickLisner(new TitleBar.TitleClickLisner() {
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

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

    }
}
