package com.example.dimensionleague;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dimensionleague.userbean.LoginBean;
import com.example.dimensionleague.userbean.RegisterBean;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.framework.port.IPresenter;

public class ceshiActivity extends BaseNetConnectActivity {

    private IPresenter<LoginBean> iPresenter;
    private IPresenter<RegisterBean> registerBeanIPresenter;
    private Button login;
    private Button register;


    @Override
    public int getLayoutId() {
        return R.layout.activity_ceshi;
    }

    @Override
    public int getRelativeLayout() {
        return R.id.layout;
    }

    @Override
    public void init() {
        super.init();
        iPresenter = new ceshiPresenter();
        registerBeanIPresenter = new RegisterPresenter();
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
    }

    @Override
    public void initDate() {
        super.initDate();
        iPresenter.attachView(this);
        registerBeanIPresenter.attachView(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerBeanIPresenter.doHttpPostRequest(1001);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iPresenter.doHttpPostRequest(1002);
            }
        });

    }

    @Override
    public boolean isConnectStatus() {
        return super.isConnectStatus();
    }

    @Override
    public String isNetType() {
        return super.isNetType();
    }

    @Override
    public void onRequestSuccess(Object data) {

    }

    @Override
    public void onRequestSuccess(int requestCode, Object data) {
        super.onRequestSuccess(requestCode, data);
        switch (requestCode){
            case 1001:
                RegisterBean loginBean1 = (RegisterBean) data;
                String message1 = loginBean1.result;
                Toast.makeText(this, message1, Toast.LENGTH_SHORT).show();
                Log.d("lhf",loginBean1.toString());
                break;
            case 1002:
                LoginBean loginBean = (LoginBean) data;
                String message = loginBean.message;
//                Log.d("lhf",loginBean.result.name+"11111"+loginBean.result.password);
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
