package com.example.shopmall.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.base.IPostBaseView;
import com.example.framework.bean.ResultBean;
import com.example.framework.manager.ShoppingManager;
import com.example.framework.manager.UserManager;
import com.example.shopmall.R;
import com.example.framework.bean.LoginBean;
import com.example.shopmall.presenter.LoginPresenter;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jiguang.analytics.android.api.LoginEvent;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity implements IPostBaseView<LoginBean> {

    private TitleBar tbLogin;
    private Button btLogin;
    private EditText etLoginName;
    private EditText etLoginWord;
    private Button btLoginRegister;
    private ImageView ivLoginWord;
    private boolean isView = false;
    private LoginPresenter loginPresenter;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == 100) {
                new CountDownTimer(1000 * 3, 1000) {

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onTick(long l) {
                        //设置登录按钮为灰色
                        btLogin.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                        //设置注册跳转按钮为灰色
                        btLoginRegister.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                    }

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onFinish() {
                        //解除登录按钮2秒锁定
                        btLogin.setEnabled(true);
                        //解除注册跳转按钮2,秒锁定
                        btLoginRegister.setEnabled(true);
                        //恢复登录按钮为红色
                        btLogin.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F44336")));
                        //恢复注册跳转按钮为绿色
                        btLoginRegister.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
                    }
                }.start();
            }

        }
    };

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        tbLogin = findViewById(R.id.tb_login);
        btLogin = findViewById(R.id.bt_login);
        etLoginName = findViewById(R.id.et_login_name);
        etLoginWord = findViewById(R.id.et_login_word);
        btLoginRegister = findViewById(R.id.bt_login_register);
        ivLoginWord = findViewById(R.id.iv_login_word);
    }

    @Override
    public void initData() {
        tbLogin.setBackgroundColor(Color.WHITE);
        tbLogin.setLeftImg(R.drawable.left);
        tbLogin.setCenterText("登录", 18, Color.BLACK);

        tbLogin.setTitleClickLisner(new TitleBar.TitleClickLisner() {
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

        //登录
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initButton();
                String name = etLoginName.getText().toString();
                String pwd = etLoginWord.getText().toString();
                loginPresenter = new LoginPresenter(name, pwd);
                loginPresenter.attachPostView(LoginActivity.this);
                loginPresenter.getCipherTextData();
            }
        });

        //注册
        btLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initButton();
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        //密码显示隐藏
        ivLoginWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isView) {
                    isView = true;
                    ivLoginWord.setBackground(getResources().getDrawable(R.drawable.view));
                    etLoginWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    initSelection();
                } else {
                    isView = false;
                    ivLoginWord.setBackground(getResources().getDrawable(R.drawable.view_off));
                    etLoginWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    initSelection();
                }
            }
        });
    }

    //密码光标在最后
    private void initSelection() {
        if (etLoginWord.getText().length() == 0) {
            etLoginWord.setSelection(0);
        } else {
            etLoginWord.setSelection(etLoginWord.getText().length());
        }
    }

    //button被点击后
    private void initButton() {
        //登录按钮2秒锁定
        btLogin.setEnabled(false);
        //注册跳转按钮2秒锁定
        btLoginRegister.setEnabled(false);
        handler.sendEmptyMessage(100);
    }

    @Override
    public void onPostDataSucess(final LoginBean data) {
        Toast.makeText(this, data.getMessage(), Toast.LENGTH_SHORT).show();
        if (data.getMessage().equals("登录成功")) {
            LoginEvent loginEvent = new LoginEvent("用户登录", true);
            JAnalyticsInterface.onEvent(LoginActivity.this, loginEvent);
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    ResultBean result = data.getResult();
                    UserManager.getInstance().setActiveUser(LoginActivity.this,result);
                    String token = result.getToken();
                    UserManager.getInstance().savaToken(token);
                    SharedPreferences token1 = getSharedPreferences("login", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = token1.edit();
                    edit.putBoolean("isLogin", true);
                    edit.apply();
                }
            }.start();
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            ShoppingManager.getInstance().setMainitem(4);
            finish();
        } else {
            LoginEvent loginEvent = new LoginEvent("用户登录", false);
            JAnalyticsInterface.onEvent(LoginActivity.this, loginEvent);
        }
    }

    @Override
    public void onPostDataFailed(String ErrorMsg) {
        LoginEvent loginEvent = new LoginEvent("用户登录", false);
        JAnalyticsInterface.onEvent(LoginActivity.this, loginEvent);
        etLoginName.setText("");
        etLoginWord.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loginPresenter != null){
            loginPresenter.detachView();
        }
        handler.removeCallbacksAndMessages(this);

    }
}
