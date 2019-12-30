package com.example.administrator.shaomall.login;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.shaomall.R;
import com.example.administrator.shaomall.activity.MainActivity;
import com.example.administrator.shaomall.login.diyview.DIYButton;
import com.example.administrator.shaomall.login.presenter.LoginPresenter;
import com.example.commen.util.ShopMailError;
import com.shaomall.framework.base.BaseMVPActivity;
import com.shaomall.framework.bean.LoginBean;
import com.shaomall.framework.manager.ActivityInstanceManager;
import com.shaomall.framework.manager.ShoppingManager;
import com.shaomall.framework.manager.UserInfoManager;

public class LoginActivity extends BaseMVPActivity<LoginBean> {
    private DIYButton diybutton;

    private EditText loginUser;
    private EditText loginPass;
    private TextView loginSignIn;
    private CheckBox loginRemindPassWord;

    private String password;
    private String username;
    LoginPresenter presenter;

    @Override
    protected void initView() {
        diybutton = findViewById(R.id.diybutton);
        loginUser = findViewById(R.id.loginUser);
        loginPass = findViewById(R.id.loginPass);
        loginSignIn = findViewById(R.id.loginSignin);
        loginRemindPassWord = (CheckBox) findViewById(R.id.loginRemindPassWord);
        presenter = new LoginPresenter();
        presenter.attachView(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initData() {
        //记住密码功能
        SharedPreferences sp=getPreferences(0);
        int remindpass = sp.getInt("remindPass", 0);
        if (remindpass==1){
            //记住密码
            String loginUser = sp.getString("loginUser", "");
            String loginPass = sp.getString("loginPass", "");
            presenter.setUsername(loginUser);
            presenter.setPassname(loginPass);
            presenter.doPostHttpRequest(100);
            ActivityInstanceManager.removeActivity(LoginActivity.this);
        }

        diybutton.setButtomtext("登录");

        loginSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册界面
                toClass(SignInActivity.class);
            }
        });

        //判断登录按钮按下和抬起
        diybutton.setOnTouchListener(new View.OnTouchListener() {



            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //这是按下时的颜色
                    //传过去false代表是按下
                    diybutton.setType(false);
                    diybutton.invalidate();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //这是抬起时的颜色

                    //true抬起
                    diybutton.setType(true);

                    diybutton.invalidate();
                    //判断用户名和密码逻辑
                    if (loginUser.getText().toString().equals("") || loginPass.getText().toString().equals("")) {
                        Toast.makeText(mContext, "用户名和密码或密码不可为空", Toast.LENGTH_SHORT).show();
                    } else {

                        username = loginUser.getText().toString();
                        password = loginPass.getText().toString();
                        presenter.setUsername(username);
                        presenter.setPassname(password);
                        presenter.doPostHttpRequest(100);
                        ActivityInstanceManager.removeActivity(LoginActivity.this);

                    }
                }
                return false;
            }
        });
    }


    @SuppressLint("CommitPrefEdits")
    @Override
    public void onRequestHttpDataSuccess(int requestCode, String message, LoginBean data) {
        SharedPreferences sp=getPreferences(0);
        SharedPreferences.Editor edit = sp.edit();
        if (loginRemindPassWord.isChecked()){


            edit.putInt("remindPass",1);
            edit.putString("loginUser",username);
            edit.putString("loginPass",password);
            edit.apply();
        }else {
            edit.putInt("remindPass",0);
            edit.apply();
        }

        //登录成功
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        UserInfoManager.getInstance().saveUserInfo(data);

        setNewActivity();

        //加载用户的购物车数据
        ShoppingManager.getInstance().getData();
    }

    /**
     * 跳转界面
     */
    private void setNewActivity() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int index = bundle.getInt("index");
            toClass(MainActivity.class, index);
        } else {
            ActivityInstanceManager.removeActivity(this);
        }
    }


    @Override
    public void onRequestHttpDataFailed(int requestCode, ShopMailError error) {
        loginUser.setText("");
        loginPass.setText("");

        //登录失败
        toast(error.getErrorMessage(), false);
        toClass(LoginActivity.class);
    }

    @Override
    public void onBackPressed() {
        ActivityInstanceManager.removeActivity(this);
        super.onBackPressed();
    }


    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }

        super.onDestroy();
    }
}
