package com.example.point.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.common.code.Constant;
import com.example.common.view.MyToolBar;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.framework.manager.AccountManager;
import com.example.framework.manager.DaoManager;
import com.example.framework.port.IPresenter;
import com.example.point.PointPresenter;
import com.example.point.R;
import com.example.point.bean.UpdatePointBean;
import com.example.framework.bean.StepBean;


import java.util.List;

public class IntegralActivity extends BaseNetConnectActivity {
    private ImageView integral_img;
    private TextView integral_title;





    private UpdatePointBean pointBean;
    private IPresenter iPresenter;


    @Override
    public int getLayoutId() {
        return R.layout.integral_activity;
    }

    @Override
    public int getRelativeLayout() {
        return R.id.integralLinear;
    }

    @Override
    public void init() {
        super.init();

        MyToolBar integral_tool = (MyToolBar) findViewById(R.id.integral_tool);
        integral_tool.init(Constant.OTHER_STYLE);
        integral_tool.getOther_title().setText("积分页面");
        integral_tool.setBackground(getResources().getDrawable(R.drawable.toolbar_style));


        //返回我的页面
        integral_tool.getOther_back().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        integral_img = findViewById(R.id.integral_img);
        TextView integral_point = findViewById(R.id.integral_point);
        RelativeLayout exchange_gift = findViewById(R.id.exchange_gift);
        RelativeLayout exchange_record = findViewById(R.id.exchange_record);
        RelativeLayout exchange_point = findViewById(R.id.exchange_point);
        integral_title = findViewById(R.id.integral_title);
        ifUser();
        if (pointBean == null) {
            pointBean = new UpdatePointBean();
        }
        List<StepBean> beans = DaoManager.Companion.getInstance(this).loadStepBean();
        //步数累加
        long count = 0;
        for (StepBean bean : beans) {
            count += bean.getStep();
        }
        if (AccountManager.getInstance().isLogin()) {
            if (AccountManager.getInstance().getUser().getPoint() != null) {
                String point = (String) AccountManager.getInstance().getUser().getPoint();
                int i = Integer.parseInt(point);
                integral_point.setText(((i + (count / 100))) + "");
            }
        } else {
            integral_point.setText(((count / 100)) + "");
        }
        iPresenter = new PointPresenter(integral_point.getText().toString());
        iPresenter.attachView(IntegralActivity.this);
        iPresenter.doHttpPostRequest();

        //兑换礼品
        exchange_gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntegralActivity.this, PresentActivity.class);
                startActivity(intent);
            }
        });
        //兑换记录
        exchange_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntegralActivity.this, ConversionActivity.class);
                startActivity(intent);
            }
        });
        //计步功能
        exchange_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntegralActivity.this, StepActivity.class);
                startActivity(intent);
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
//        UpdatePointBean pointBean= (UpdatePointBean) data;
//        String result = pointBean.result;
//        integral_point.setText(result+"分");
    }

    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void showError() {
        super.showError();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iPresenter != null) {
            iPresenter.detachView();
        }
    }

    private void ifUser() {
        if (AccountManager.getInstance().isLogin()) {
            if (AccountManager.getInstance().getUser().getName() != null) {
                //登录
                integral_title.setText(AccountManager.getInstance().getUser().getName());
                if (AccountManager.getInstance().getUser().getAvatar() != null) {
                    Glide.with(this).load(AccountManager.getInstance().getUser().getAvatar()).into(integral_img);
                }
            }
        } else {
            //没有登录
            integral_title.setText("登录/注册");
            integral_img.setImageResource(R.mipmap.wu);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
