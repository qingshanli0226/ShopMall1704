package com.example.shopmall.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.base.IPostBaseView;
import com.example.framework.bean.UserBean;
import com.example.framework.manager.UserManager;
import com.example.shopmall.R;
import com.example.shopmall.bean.RegisterBean;
import com.example.shopmall.presenter.RegisterPresenter;

public class RegisterActivity extends BaseActivity implements IPostBaseView<RegisterBean> {

    TitleBar mTitleBar;
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
        mTitleBar = findViewById(R.id.tb_register);
        mName = findViewById(R.id.et_name);
        mPassWord = findViewById(R.id.et_word);
        mRegister = findViewById(R.id.bt_register);
    }

    @Override
    public void initData() {

        mTitleBar.setBackgroundColor(Color.RED);
        mTitleBar.setLeftImg(R.drawable.left);
        mTitleBar.setCenterText("注册", 18, Color.WHITE);

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
                integerPresenter.attachPostView(RegisterActivity.this);
                integerPresenter.getCipherTextData();
            }
        });


    }

    @Override
    public void onPostDataSucess(RegisterBean data) {
        Toast.makeText(this, data.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostDataFailed(String ErrorMsg) {

    }
}
