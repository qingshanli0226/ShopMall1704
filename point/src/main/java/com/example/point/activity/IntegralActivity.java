package com.example.point.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framework.base.BaseNetConnectActivity;
import com.example.framework.port.IPresenter;
import com.example.point.PointPresenter;
import com.example.point.R;
import com.example.point.UpdatePointBean;
import com.example.point.service.StepBean;
import com.example.point.stepmanager.DaoManager;

import java.util.List;

public class IntegralActivity extends BaseNetConnectActivity {
    private ImageView iv_left;
    private TextView physical;
    private ImageView iv_right;
    private LinearLayout layout_titlebar;
    private ImageView integral_img;
    private TextView integral_point;
    private RelativeLayout exchange_gift;
    private RelativeLayout exchange_record;
    private RelativeLayout exchange_point;

    private UpdatePointBean pointBean;
    private IPresenter iPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.integral_activity;
    }

    @Override
    public void init() {
        iv_left = findViewById(R.id.iv_left);
        physical = findViewById(R.id.physical);
        iv_right = findViewById(R.id.iv_right);
        layout_titlebar = findViewById(R.id.layout_titlebar);
        integral_img = findViewById(R.id.integral_img);
        integral_point = findViewById(R.id.integral_point);
        exchange_gift = findViewById(R.id.exchange_gift);
        exchange_record = findViewById(R.id.exchange_record);
        exchange_point = findViewById(R.id.exchange_point);

        if (pointBean==null){
            pointBean=new UpdatePointBean();
        }
        layout_titlebar.setBackgroundColor(Color.rgb(247, 195, 93));
        physical.setText("积分统计");
        List<StepBean> beans = new DaoManager(this).loadStepBean();
        //步数累加
        long count=0;
        for (StepBean bean:beans) {
            count+=bean.getStep();
        }
       integral_point.setText((count/100)+"分");

        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

//        iPresenter=new PointPresenter(pointBean.result);
//        iPresenter.attachView(this);
//        iPresenter.doHttpPostRequest();

        //兑换礼品
        exchange_gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //兑换记录
        exchange_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //计步功能
        exchange_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntegralActivity.this,StepActivity.class);
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
      //  integral_point.setText(result+"分");
    }

    @Override
    public void initDate() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.detachView();
    }

    @Override
    public void showLoading() {
        super.showLoading();
    }
}
