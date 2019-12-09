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
import com.example.framework.base.IBaseView;
import com.example.shopmall.R;
import com.example.shopmall.bean.LoginBean;
import com.example.shopmall.presenter.LoginPresenter;
import com.example.shopmall.presenter.RegisterPresenter;

public class LoginActivity extends BaseActivity implements IBaseView<LoginBean> {

    TitleBar tb_login;
    Button bt_login;
    EditText mName;
    EditText mPassWord;
    Button mRegister;

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        tb_login = findViewById(R.id.tb_login);
        bt_login = findViewById(R.id.bt_login);
        mName = findViewById(R.id.et_login_name);
        mPassWord = findViewById(R.id.et_login_word);
        mRegister = findViewById(R.id.bt_login_register);
    }

    @Override
    public void initData() {
        tb_login.setBackgroundColor(Color.RED);
        tb_login.setLeftImg(R.drawable.left);
        tb_login.setCenterText("登录", 18, Color.WHITE);

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
                String name = mName.getText().toString();
                String pwd = mPassWord.getText().toString();
                LoginPresenter loginPresenter = new LoginPresenter(name, pwd);
                loginPresenter.attachView(LoginActivity.this);
                loginPresenter.login();
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
    public void onGetDataSucess(LoginBean data) {

    }

    @Override
    public void onPostDataSucess(LoginBean data) {
        Toast.makeText(this, data.getMessage(), Toast.LENGTH_SHORT).show();
        LoginBean.ResultBean result = data.getResult();
        String token = result.getToken();
        SharedPreferences token1 = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = token1.edit();
        edit.putString("getToken", token).apply();
        Log.e("####", token);
    }

    @Override
    public void onGetDataFailed(String ErrorMsg) {

    }

    @Override
    public void onLoadingPage() {

    }

    @Override
    public void onStopLoadingPage() {

    }
}
