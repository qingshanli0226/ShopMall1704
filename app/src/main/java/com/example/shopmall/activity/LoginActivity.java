package com.example.shopmall.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.base.IPostBaseView;
import com.example.framework.bean.ResultBean;
import com.example.framework.manager.UserManager;
import com.example.shopmall.R;
import com.example.framework.bean.LoginBean;
import com.example.shopmall.presenter.LoginPresenter;

public class LoginActivity extends BaseActivity implements IPostBaseView<LoginBean> {

    private TitleBar tbLogin;
    private Button btLogin;
    private EditText etLoginName;
    private EditText etLoginWord;
    private Button btLoginRegister;

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
    }

    @Override
    public void initData() {
        tbLogin.setBackgroundColor(Color.RED);
        tbLogin.setLeftImg(R.drawable.left);
        tbLogin.setCenterText("登录", 18, Color.WHITE);

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

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etLoginName.getText().toString();
                String pwd = etLoginWord.getText().toString();
                LoginPresenter loginPresenter = new LoginPresenter(name, pwd);
                loginPresenter.attachPostView(LoginActivity.this);
                loginPresenter.getCipherTextData();
                etLoginName.setText("");
                etLoginWord.setText("");
            }
        });

        btLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }

    @Override
    public void onPostDataSucess(LoginBean data) {
        Toast.makeText(this, data.getMessage(), Toast.LENGTH_SHORT).show();
        ResultBean result = data.getResult();
        String token = result.getToken();
        if (data.getMessage().equals("登录成功")) {
            UserManager.getInstance().setActiveUser(result);
            UserManager.getInstance().addUser(result);
            SharedPreferences token1 = getSharedPreferences("login", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = token1.edit();
            edit.putString("getToken", token).apply();
            finish();
        }
    }

    @Override
    public void onPostDataFailed(String ErrorMsg) {
        etLoginName.setText("");
        etLoginWord.setText("");
    }
}
