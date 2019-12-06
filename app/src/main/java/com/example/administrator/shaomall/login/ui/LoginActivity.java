package com.example.administrator.shaomall.login.ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.shaomall.R;
import com.example.administrator.shaomall.login.Base.LoginBean;
import com.example.administrator.shaomall.login.diyview.DIYButton;
import com.example.administrator.shaomall.login.presenter.LoginPresenter;
import com.example.commen.ShopMailError;
import com.shaomall.framework.base.BaseActivity;
import com.shaomall.framework.base.BaseMVPActivity;

public class LoginActivity extends BaseMVPActivity<LoginBean.ResultBean> {
    private DIYButton diybutton;
    LinearGradient linearGradient;
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
        presenter=new LoginPresenter();
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
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    //这是按下时的颜色
                    int colorStart = getResources().getColor(R.color.mediumspringgreen);
                    //传过去true代表是按下
                    diybutton.setType(true);
                    int color = Color.parseColor("#00ced1");
                    int colorEnd = getResources().getColor(R.color.skyblue);

                    //Toast.makeText(mActivity, "123", Toast.LENGTH_SHORT).show();
                    diybutton.invalidate();
                }else if (event.getAction()==MotionEvent.ACTION_UP){
                    //这是抬起时的颜色
                    int colorStart = getResources().getColor(R.color.mediumspringgreen);
                    //false抬起
                    diybutton.setType(false);
                    int color = Color.parseColor("#00ced1");
                    int colorEnd = getResources().getColor(R.color.skyblue);

                    //Toast.makeText(mActivity, "松开了", Toast.LENGTH_SHORT).show();
                    diybutton.invalidate();
                    //判断用户名和密码逻辑
                    if (loginUser.getText().toString().equals("")||loginPass.getText().toString().equals("")){
                        Toast.makeText(mActivity, "用户名和密码或密码不可为空", Toast.LENGTH_SHORT).show();
                    }else {
                        String username = loginUser.getText().toString();
                        String password = loginPass.getText().toString();
                        presenter.setUsername(username);
                        presenter.setPassname(password);
                        presenter.doPostHttpRequest(100);

                    }
            }
                return false;
            }
        });
    }

    @Override
    public void onRequestHttpDataFailed(int requestCode, ShopMailError error) {
        Toast.makeText(mActivity, ""+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestHttpDataSuccess(int requestCode, String message, LoginBean.ResultBean data) {

        Toast.makeText(mActivity, ""+message, Toast.LENGTH_SHORT).show();
    }
}
