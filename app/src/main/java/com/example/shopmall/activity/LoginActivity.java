package com.example.shopmall.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.base.IPostBaseView;
import com.example.shopmall.R;
import com.example.shopmall.bean.LoginBean;
import com.example.shopmall.presenter.LoginPresenter;

public class LoginActivity extends BaseActivity implements IPostBaseView<LoginBean> {

    TitleBar mTitleBar;
    Button mLogin;
    EditText mName;
    EditText mPassWord;
    Button mRegister;

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        mTitleBar = findViewById(R.id.tb_login);
        mLogin = findViewById(R.id.bt_login);
        mName = findViewById(R.id.et_login_name);
        mPassWord = findViewById(R.id.et_login_word);
        mRegister = findViewById(R.id.bt_login_register);
    }

    @Override
    public void initData() {
        mTitleBar.setBackgroundColor(Color.RED);
        mTitleBar.setLeftImg(R.drawable.left);
        mTitleBar.setCenterText("登录", 18, Color.WHITE);

        mTitleBar.setTitleClickLisner(new TitleBar.TitleClickLisner() {
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

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mName.getText().toString();
                String pwd = mPassWord.getText().toString();
                LoginPresenter loginPresenter = new LoginPresenter(name, pwd);
                loginPresenter.attachPostView(LoginActivity.this);
                loginPresenter.getCipherTextData();
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }

    @Override
    public void onPostDataSucess(LoginBean data) {
        Toast.makeText(this, data.getMessage(), Toast.LENGTH_SHORT).show();
        LoginBean.ResultBean result = data.getResult();
        String token = result.getToken();
        SharedPreferences token1 = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = token1.edit();
        edit.putString("getToken", token).apply();//
        Log.e("####", token);
    }

    @Override
    public void onPostDataFailed(String ErrorMsg) {

    }
}
