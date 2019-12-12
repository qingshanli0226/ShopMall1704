package com.example.dimensionleague.register.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.common.Constant;
import com.example.common.port.IButtonEnabledListener;
import com.example.dimensionleague.R;
import com.example.dimensionleague.login.activity.LoginActivity;
import com.example.dimensionleague.register.presenter.RegisterPresenter;
import com.example.dimensionleague.userbean.RegisterBean;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.framework.base.BaseTextWatcher;
import com.example.framework.port.IPresenter;
import com.example.net.AppNetConfig;

import java.util.HashMap;

/**
 * author:李浩帆
 */
public class RegisterActivity extends BaseNetConnectActivity implements View.OnClickListener, IButtonEnabledListener {

    private ImageView register_back;
    private EditText user_name;
    private EditText password;
    private EditText qr_password;
    private Button btn_register;

    private IPresenter iPresenter;

    private boolean isContentUser = false;
    private boolean isContentPassword = false;
    private boolean isContentAffirmPassword = false;
    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public int getRelativeLayout() {
        return R.id.register_layout;
    }

    @Override
    public void init() {
        super.init();
        register_back = (ImageView) findViewById(R.id.register_back);
        user_name = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.password);
        qr_password = (EditText) findViewById(R.id.qr_password);
        btn_register = (Button) findViewById(R.id.btn_register);

        register_back.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void initDate() {
        user_name.addTextChangedListener(new BaseTextWatcher(){
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
        password.addTextChangedListener(new BaseTextWatcher(){
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
        qr_password.addTextChangedListener(new BaseTextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s)){
                    isContentAffirmPassword = true;
                    onEnableChanged();
                }else{
                    isContentAffirmPassword = false;
                    onEnableChanged();
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                String name = user_name.getText().toString().trim();
                String pwd = password.getText().toString().trim();
                String qrPassword = qr_password.getText().toString().trim();
                if(!pwd.equals(qrPassword)){
                    Toast.makeText(this, "两次密码不相同，无法注册", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(Constant.KEY_USERNAME,name);
                hashMap.put(Constant.KEY_PASSWORD,pwd);
                iPresenter = new RegisterPresenter(hashMap);
                iPresenter.attachView(this);
                iPresenter.doHttpPostRequest();
                break;
            case R.id.register_back:
                finishActivity();
                break;
        }
    }

    @Override
    public void onRequestSuccess(Object data) {
        RegisterBean registerBean = (RegisterBean)data;
        if(registerBean.getCode().equals(Constant.CODE_OK)){
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("userName",user_name.getText().toString().trim());
            intent.putExtra("password",password.getText().toString().trim());
            boundActivity(intent);
            finish();
        }else{
            Log.d("aaa", "onRequestSuccess: "+registerBean.toString());
        }
    }

    @Override
    public void onEnableChanged() {
        if(isContentUser && isContentPassword && isContentAffirmPassword){
            btn_register.setEnabled(true);
        }else{
            btn_register.setEnabled(false);
        }
    }
}
