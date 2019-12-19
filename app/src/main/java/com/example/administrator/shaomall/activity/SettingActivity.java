package com.example.administrator.shaomall.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.administrator.shaomall.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.shaomall.framework.base.BaseActivity;

import java.util.ArrayList;

public class SettingActivity extends BaseActivity {
    private LinearLayout settingLinearSex;
    private TextView settingTextsex;
    private LinearLayout settingLinearDate;
    private TextView settingTextDate;
    private DatePicker settingDatePicker;
    private LinearLayout settingLinearName;
    private TextView settingTextChangeName;
    private SimpleDraweeView settingPhoto;
    private TextView settingyong;
    private ImageView settingback;


    ArrayList<String> arr=new ArrayList<String>();
    @Override
    protected int setLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        settingback = (ImageView) findViewById(R.id.settingback);
        settingLinearName = (LinearLayout) findViewById(R.id.settingLinearName);
        settingTextChangeName = (TextView) findViewById(R.id.settingTextChangeName);
        settingDatePicker = (DatePicker) findViewById(R.id.settingDatePicker);
        settingLinearSex = (LinearLayout) findViewById(R.id.settingLinearSex);
        settingTextsex = (TextView) findViewById(R.id.settingTextsex);
        settingLinearDate = (LinearLayout) findViewById(R.id.settingLinearDate);
        settingTextDate = (TextView) findViewById(R.id.settingTextDate);
        settingPhoto = (SimpleDraweeView) findViewById(R.id.settingPhoto);
        settingyong = (TextView) findViewById(R.id.settingyong);


        arr.add("男");
        arr.add("女");
        arr.add("保密");
    }

    @Override
    protected void initData() {
        //退出按钮
        settingback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String name = extras.getString("name");
        String head = extras.getString("head");
        settingTextChangeName.setText(name);
        settingPhoto.setImageURI(head);
        settingyong.setText(name);
        Toast.makeText(mActivity, ""+head, Toast.LENGTH_SHORT).show();
        //点击修改名字的事件
        settingLinearName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //这个是性别的点击事件
        settingLinearSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPickerView();
            }
        });
        //这个就是日期选择器
        settingLinearDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //让他可见
                settingDatePicker.setVisibility(View.VISIBLE);
                //年
                int year = settingDatePicker.getYear();
                //月
                int month = settingDatePicker.getMonth();
                //天
                int dayOfMonth = settingDatePicker.getDayOfMonth();


                Toast.makeText(mActivity, year+"  "+month+"  "+"  "+dayOfMonth, Toast.LENGTH_SHORT).show();
            }
        });

    }

    // 弹出选择器
    private void showPickerView() {

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                settingTextsex.setText(arr.get(options1));

            }
        })
                .setTitleText("选择性别")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(arr);//三级选择器
        pvOptions.show();
    }
}
