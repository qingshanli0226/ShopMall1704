package com.example.dimensionleague.login.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.Constant;
import com.example.common.User;
import com.example.common.manager.AccountManager;
import com.example.common.port.IButtonEnabledListener;
import com.example.dimensionleague.R;
import com.example.dimensionleague.login.presenter.LoginPresenter;
import com.example.dimensionleague.register.activity.RegisterActivity;
import com.example.dimensionleague.userbean.LoginBean;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.framework.base.BaseTextWatcher;
import com.example.framework.port.IPresenter;
import com.example.net.AppNetConfig;

import java.util.HashMap;

/**
 * author:李浩帆
 */
public class LoginActivity extends BaseNetConnectActivity implements IButtonEnabledListener,View.OnClickListener {

    private ImageView login_back;
    private EditText user_name;
    private EditText password;
    private CheckBox password_check;
    private TextView forget_password;
    private Button btn_login;
    private TextView user_register;
    //TODO 登录的Presenter
    private IPresenter loginPresenter;

    private AccountManager accountManager = AccountManager.getInstance();

    private boolean isContentUser = false;
    private boolean isContentPassword = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public int getRelativeLayout() {
        return R.id.login_layout;
    }

    @Override
    public void init() {
        super.init();
        login_back = (ImageView) findViewById(R.id.login_back);
        user_name = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.password);
        password_check = (CheckBox) findViewById(R.id.password_check);
        forget_password = (TextView) findViewById(R.id.forget_password);
        btn_login = findViewById(R.id.btn_login);
        user_register = findViewById(R.id.user_register);

        login_back.setOnClickListener(this);
        user_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void initDate() {
        super.initDate();
        user_name.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s)){
                    isContentUser = true;
                    onEnableChanged();
                }else{
                    isContentUser = false;
                    onEnableChanged();
                }
            }
        });

        password.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s)){
                    isContentPassword = true;
                    onEnableChanged();
                }else{
                    isContentPassword = false;
                    onEnableChanged();
                }
            }
        });
    }


    @Override
    public void onRequestSuccess(Object data) {
        LoginBean loginBean = (LoginBean)data;
        Log.d("lhf", "onRequestSuccess: "+loginBean.toString());
        LoginBean.ResultBean result = loginBean.getResult();
        if(loginBean.getCode().equals(Constant.CODE_OK)){
            accountManager.setUser(new User());
            accountManager.saveToken(result.getToken());
            accountManager.notifyLogin();
        }
    }

    @Override
    public void onConnected() {
        hideEmpty();
    }

    @Override
    public void onDisConnected() {
        showEmpty();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onEnableChanged() {
        if(isContentUser && isContentPassword){
            btn_login.setEnabled(true);
        }else{
            btn_login.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                String userName = user_name.getText().toString();
                String pwd = password.getText().toString();
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(Constant.KEY_USERNAME,userName);
                hashMap.put(Constant.KEY_PASSWORD,pwd);
                loginPresenter = new LoginPresenter(hashMap);
                loginPresenter.attachView(LoginActivity.this);
                //TODO post请求数据
                loginPresenter.doHttpPostRequest();
                break;
            case R.id.user_register:
                startActivity(RegisterActivity.class,null);
                break;
            case R.id.login_back:
                finishActivity();
                break;
        }
    }
}
