package com.example.shopmall.activity;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.base.IBaseView;
import com.example.framework.manager.UserBean;
import com.example.framework.manager.UserManager;
import com.example.shopmall.R;
import com.example.shopmall.bean.RegisterBean;
import com.example.shopmall.presenter.IntegerPresenter;
import com.example.shopmall.presenter.RegisterPresenter;

import java.util.HashMap;

public class RegisterActivity extends BaseActivity implements IBaseView<RegisterBean> {

    TitleBar tb_register;
    EditText mName;
    EditText mPassWord;
    Button mRegister;
    RegisterPresenter integerPresenter;

    @Override
    protected int setLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        tb_register = findViewById(R.id.tb_register);
        mName = findViewById(R.id.et_name);
        mPassWord = findViewById(R.id.et_word);
        mRegister = findViewById(R.id.bt_register);
    }

    @Override
    public void initData() {

        tb_register.setBackgroundColor(Color.RED);
        tb_register.setLeftImg(R.drawable.left);
        tb_register.setCenterText("注册", 18, Color.WHITE);

        tb_register.setTitleClickLisner(new TitleBar.TitleClickLisner() {
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


        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString();
                String pwd = mPassWord.getText().toString();
                UserBean userBean = new UserBean();
                userBean.setName(name);
                userBean.setPassword(pwd);
                UserManager.getInstance().addUser(userBean);
                integerPresenter = new RegisterPresenter(name, pwd);
                integerPresenter.attachView(RegisterActivity.this);
                integerPresenter.register();
            }
        });


    }


    @Override
    public void onGetDataSucess(RegisterBean data) {

    }

    @Override
    public void onPostDataSucess(RegisterBean data) {
        Toast.makeText(this, data.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetDataFailed(String ErrorMsg) {
        Log.e("####", "" + ErrorMsg);
    }

    @Override
    public void onLoadingPage() {

    }

    @Override
    public void onStopLoadingPage() {

    }
}
