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
import com.example.administrator.shaomall.login.presenter.SigninPresenter;
import com.example.commen.util.ShopMailError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.shaomall.framework.base.BaseMVPActivity;
import com.wyp.avatarstudio.AvatarStudio;

import java.io.File;

public class SigninActivity extends BaseMVPActivity<String> {

    private Headportrait signinhead;
    private SimpleDraweeView signinPhoto;


    private DIYButton diybutton;
    private EditText signinUser;
    private EditText signinPass;
    private TextView signinSignin;
    SigninPresenter presenter=new SigninPresenter();

    @Override
    protected void initView() {
        presenter.attachView(this);
        signinUser = (EditText) findViewById(R.id.signinUser);
        signinPass = (EditText) findViewById(R.id.signinPass);
        signinSignin = (TextView) findViewById(R.id.signinSignin);
        diybutton = (DIYButton) findViewById(R.id.diybutton);
        signinhead = (Headportrait) findViewById(R.id.signinhead);
        signinPhoto = (SimpleDraweeView) findViewById(R.id.signinPhoto);




    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_signin;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initData() {
        diybutton.setButtomtext("注册");

        signinSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //头像
        signinhead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AvatarStudio.Builder builder = new AvatarStudio.Builder(SigninActivity.this);
                builder.setTextColor(R.color.darkturquoise);
                builder.setText("拍照","本地选择","取消");
                builder.needCrop(true);
                builder.dimEnabled(true);
                builder.setOutput(100,100);
                builder.setAspect(1,1);
                builder.show(new AvatarStudio.CallBack() {
                    @Override
                    public void callback(String uri) {
                        signinPhoto.setVisibility(View.VISIBLE);

                        signinPhoto.setImageURI(Uri.fromFile(new File(uri)));
                        signinhead.setVisibility(View.GONE);

                    }
                });
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
                    if (signinUser.getText().toString().equals("")||signinPass.getText().toString().equals("")){
                        Toast.makeText(mActivity, "用户名和密码或密码不可为空", Toast.LENGTH_SHORT).show();
                    }else {
                        String username = signinUser.getText().toString();
                        String password = signinPass.getText().toString();
                        presenter.setUsername(username);
                        presenter.setPassword(password);
                        presenter.doPostHttpRequest(100);
                        finish();
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onRequestHttpDataSuccess(int requestCode, String message, String data) {
        //注册成功
        Toast.makeText(mActivity, ""+message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestHttpDataFailed(int requestCode, ShopMailError error) {
        //注册失败
        Toast.makeText(mActivity, ""+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }
}
