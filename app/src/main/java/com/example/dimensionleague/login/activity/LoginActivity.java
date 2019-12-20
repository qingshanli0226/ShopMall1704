package com.example.dimensionleague.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buy.activity.OrderActivity;
import com.example.buy.activity.SearchActivity;
import com.example.common.code.Constant;
import com.example.common.User;
import com.example.common.code.ErrorCode;
import com.example.common.utils.IntentUtil;
import com.example.common.view.MyToast;
import com.example.dimensionleague.setting.SettingActivity;
import com.example.framework.manager.AccountManager;
import com.example.common.port.IButtonEnabledListener;
import com.example.dimensionleague.R;
import com.example.dimensionleague.login.presenter.LoginPresenter;
import com.example.dimensionleague.register.activity.RegisterActivity;
import com.example.dimensionleague.userbean.LoginBean;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.framework.base.BaseTextWatcher;
import com.example.framework.port.IPresenter;
import com.example.point.activity.IntegralActivity;
import com.example.point.activity.StepActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

/**
 * author:李浩帆
 */
public class LoginActivity extends BaseNetConnectActivity implements IButtonEnabledListener,View.OnClickListener {

    private EditText user_name;
    private EditText password;
    private CheckBox password_check;
    private TextView forget_password;
    private Button btn_login;
    //TODO 登录的Presenter
    private IPresenter loginPresenter;

    private String whence = "默认";

    private final AccountManager accountManager = AccountManager.getInstance();
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
        Bundle data = getIntent().getBundleExtra("data");
        if(data!=null){
            whence = data.getString(IntentUtil.LOGIN);
        }
        ImageView login_back = findViewById(R.id.login_back);
        user_name = findViewById(R.id.user_name);
        password = findViewById(R.id.password);
        password_check = findViewById(R.id.password_check);
        forget_password = findViewById(R.id.forget_password);
        btn_login = findViewById(R.id.btn_login);
        TextView user_register = findViewById(R.id.user_register);
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
        password_check.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }else{
//                    隐藏密码
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
        forget_password.setOnClickListener(v -> toast(this,getString(R.string.contact)));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String userName = intent.getStringExtra(Constant.KEY_USERNAME);
        String password = intent.getStringExtra(Constant.KEY_PASSWORD);
        this.user_name.setText(userName);
        this.password.setText(password);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constant.KEY_NAME,userName);
        hashMap.put(Constant.KEY_PASSWORD,password);
        loginPresenter = new LoginPresenter(hashMap);
        loginPresenter.attachView(this);
        loginPresenter.doHttpPostRequest();
    }

    @Override
    public void onRequestSuccess(Object data) {
        LoginBean loginBean = (LoginBean)data;
        LoginBean.ResultBean result = loginBean.getResult();
        if(loginBean.getCode().equals(Constant.CODE_OK)){
            LoginBean.ResultBean bean = loginBean.getResult();
            //TODO 将用户信息存储到本地
            accountManager.setUser(new User(bean.getName(),bean.getPassword(),bean.getEmail(),bean.getPhone(),bean.getPoint(),bean.getAddress(),bean.getMoney(),bean.getAvatar()));
            //TODO 将Token存储
            accountManager.saveToken(result.getToken());
            //TODO 通知别的页面用户已登录
            accountManager.notifyLogin();
            if (accountManager.getUser().getAvatar()!=null){
                accountManager.notifyUserAvatarUpdate((String) accountManager.getUser().getAvatar());
            }
            Bundle bundle = new Bundle();
            switch (whence){
                case Constant.MESSAGE:
                    startActivity(SearchActivity.class,null);
                    finish();
                    break;
                case Constant.USER_SETTING:
                    startActivity(SettingActivity.class,null);
                    finish();
                    break;
                case Constant.ALL_ORDER:
                    bundle.putString(IntentUtil.ORDER_SHOW,Constant.ALL_ORDER);
                    startActivity(OrderActivity.class,bundle);
                    finish();
                    break;
                case Constant.WAIT_PAY:
                    bundle.putString(IntentUtil.ORDER_SHOW,Constant.WAIT_PAY);
                    startActivity(OrderActivity.class,bundle);
                    finish();
                    break;
                case Constant.WAIT_SEND:
                    bundle.putString(IntentUtil.ORDER_SHOW,Constant.WAIT_SEND);
                    startActivity(OrderActivity.class,bundle);
                    finish();
                    break;
                case Constant.MINE_INTEGRAL:
                    startActivity(IntegralActivity.class,null);
                    finish();
                    break;
                case Constant.EXERCISE:
                    startActivity(StepActivity.class, null);
                    finish();
                    break;
                default:
                    finishActivity();
                    break;
            }
        }else if(loginBean.getCode().equals(""+ErrorCode.ERROR_USER_NOT_REGISTERED.getErrorCode())){
            MyToast.showToast(this,loginBean.getMessage(),R.drawable.empty_image, Toast.LENGTH_SHORT);
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
        if(loginPresenter!=null){
            loginPresenter.detachView();
        }
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
                hashMap.put(Constant.KEY_NAME,userName);
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
}
