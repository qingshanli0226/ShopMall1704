package com.example.administrator.shaomall.login;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.shaomall.R;
import com.example.administrator.shaomall.login.diyview.DIYButton;
import com.example.administrator.shaomall.login.diyview.Headportrait;
import com.example.administrator.shaomall.login.presenter.SignInPresenter;
import com.example.commen.util.ShopMailError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.shaomall.framework.base.BaseMVPActivity;
import com.shaomall.framework.manager.ActivityInstanceManager;
import com.wyp.avatarstudio.AvatarStudio;

import java.io.File;

public class SignInActivity extends BaseMVPActivity<String> {

    private Headportrait signInHead;
    private SimpleDraweeView signInPhoto;

    private DIYButton diybutton;
    private EditText signInUser;
    private EditText signInPass;
    private TextView signInSignIn;
    SignInPresenter presenter;

    @Override
    protected void initView() {
        presenter = new SignInPresenter();
        presenter.attachView(this);
        signInUser = findViewById(R.id.signinUser);
        signInPass = findViewById(R.id.signinPass);
        signInSignIn = findViewById(R.id.signinSignin);
        diybutton = findViewById(R.id.diybutton);
        signInHead = findViewById(R.id.signinhead);
        signInPhoto = findViewById(R.id.signinPhoto);


    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_signin;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initData() {
        diybutton.setButtomtext("注册");

        signInSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //头像
        signInHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AvatarStudio.Builder builder = new AvatarStudio.Builder(SignInActivity.this);
                builder.setTextColor(R.color.ColorDark);
                builder.setText("拍照", "本地选择", "取消");
                builder.needCrop(true);
                builder.dimEnabled(true);
                builder.setOutput(100, 100);
                builder.setAspect(1, 1);
                builder.show(new AvatarStudio.CallBack() {
                    @Override
                    public void callback(String uri) {
                        signInPhoto.setVisibility(View.VISIBLE);
                        signInPhoto.setImageURI(Uri.fromFile(new File(uri)));
                        signInHead.setVisibility(View.GONE);
                    }
                });
            }
        });

        //点击圆形图片
        signInPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AvatarStudio.Builder builder = new AvatarStudio.Builder(SignInActivity.this);
                builder.setTextColor(R.color.ColorDark);
                builder.setText("拍照", "本地选择", "取消");
                builder.needCrop(true);
                builder.dimEnabled(true);
                builder.setOutput(100, 100);
                builder.setAspect(1, 1);
                builder.show(new AvatarStudio.CallBack() {
                    @Override
                    public void callback(String uri) {
                        signInPhoto.setVisibility(View.VISIBLE);
                        signInPhoto.setImageURI(Uri.fromFile(new File(uri)));
                        signInHead.setVisibility(View.GONE);
                    }
                });
            }
        });


        //判断登录按钮按下和抬起
        diybutton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //传过去false代表是按下
                    diybutton.setType(false);
                    diybutton.invalidate();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //true抬起
                    diybutton.setType(true);
                    diybutton.invalidate();
                    //判断用户名和密码逻辑
                    if (signInUser.getText().toString().equals("") || signInPass.getText().toString().equals("")) {
                        toast("用户名和密码或密码不可为空", false);
                    } else {
                        String username = signInUser.getText().toString();
                        String password = signInPass.getText().toString();
                        presenter.setUsername(username);
                        presenter.setPassword(password);
                        presenter.doPostHttpRequest(100);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onRequestHttpDataSuccess(int requestCode, String message, String data) {
        //注册成功
        toast(message, false);
        ActivityInstanceManager.removeActivity(this);
    }


    @Override
    public void onRequestHttpDataFailed(int requestCode, ShopMailError error) {
        //注册失败
        toast("用户已存在, 注册失败", false);
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
