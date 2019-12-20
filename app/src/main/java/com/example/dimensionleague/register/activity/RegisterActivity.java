package com.example.dimensionleague.register.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.common.code.Constant;
import com.example.common.port.IButtonEnabledListener;
import com.example.dimensionleague.R;
import com.example.dimensionleague.login.activity.LoginActivity;
import com.example.dimensionleague.register.presenter.RegisterPresenter;
import com.example.dimensionleague.userbean.RegisterBean;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.framework.base.BaseTextWatcher;
import com.example.framework.port.IPresenter;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

/**
 * author:李浩帆
 */
public class RegisterActivity extends BaseNetConnectActivity implements View.OnClickListener, IButtonEnabledListener {

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
        ImageView register_back = findViewById(R.id.register_back);
        user_name = findViewById(R.id.user_name);
        password = findViewById(R.id.password);
        qr_password = findViewById(R.id.qr_password);
        btn_register = findViewById(R.id.btn_register);
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
                    Toast.makeText(this, R.string.region_no, Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(Constant.KEY_NAME,name);
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
        hideLoading();
        RegisterBean registerBean = (RegisterBean)data;
        if(registerBean.getCode().equals(Constant.CODE_OK)){
            Toast.makeText(this, R.string.region_yes, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(Constant.KEY_USERNAME,user_name.getText().toString().trim());
            intent.putExtra(Constant.KEY_PASSWORD,password.getText().toString().trim());
            boundActivity(intent);
            finish();
        }else{
            //错误处理
            toast(this,registerBean.getMessage());
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
    @Override
    protected void onPause() {
        MobclickAgent.onPause(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onResume(this);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iPresenter!=null){
            iPresenter.detachView();
        }
    }
}
