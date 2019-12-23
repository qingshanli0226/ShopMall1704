package com.example.administrator.shaomall.login;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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
    private TextView loginSignin;
    LoginPresenter presenter;

    @Override
    protected void initView() {
        diybutton = (DIYButton) findViewById(R.id.diybutton);
        loginUser = (EditText) findViewById(R.id.loginUser);
        loginPass = (EditText) findViewById(R.id.loginPass);
        loginSignin = (TextView) findViewById(R.id.loginSignin);
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
        diybutton.setButtomtext("登录");


        loginSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册界面
                toClass(SigninActivity.class);
            }
        });

        //判断登录按钮按下和抬起
        diybutton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //这是按下时的颜色
                    int colorStart = getResources().getColor(R.color.mediumspringgreen);
                    //传过去false代表是按下
                    diybutton.setType(false);

                    int color = Color.parseColor("#00ced1");
                    int colorEnd = getResources().getColor(R.color.skyblue);

                    //Toast.makeText(mActivity, "123", Toast.LENGTH_SHORT).show();
                    diybutton.invalidate();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //这是抬起时的颜色

                    int colorStart = getResources().getColor(R.color.mediumspringgreen);
                    //true抬起
                    diybutton.setType(true);
                    int color = Color.parseColor("#00ced1");
                    int colorEnd = getResources().getColor(R.color.skyblue);

                    //Toast.makeText(mActivity, "松开了", Toast.LENGTH_SHORT).show();
                    diybutton.invalidate();
                    //判断用户名和密码逻辑
                    if (loginUser.getText().toString().equals("") || loginPass.getText().toString().equals("")) {
                        Toast.makeText(mActivity, "用户名和密码或密码不可为空", Toast.LENGTH_SHORT).show();
                    } else {
                        String username = loginUser.getText().toString();
                        String password = loginPass.getText().toString();
                        presenter.setUsername(username);
                        presenter.setPassname(password);
                        presenter.doPostHttpRequest(100);
                        toClass(MainActivity.class);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onRequestHttpDataFailed(int requestCode, ShopMailError error) {
        //登录失败
        Toast.makeText(mActivity, "" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityInstanceManager.removeActivity(this);

        //        toClass(MainActivity.class, 0); //返回首页
    }

    @Override
    public void onRequestHttpDataSuccess(int requestCode, String message, LoginBean data) {
        //登录成功
        Toast.makeText(mActivity, "" + message, Toast.LENGTH_SHORT).show();
        UserInfoManager instance = UserInfoManager.getInstance();
        instance.saveUserInfo(data);
        //加载用户的购物车数据
        ShoppingManager.getInstance().getData();

        setNewActivity();
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
}
