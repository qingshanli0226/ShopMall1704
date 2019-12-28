package com.example.administrator.shaomall.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.administrator.shaomall.R;
import com.example.commen.custom.ToolbarCustom;
import com.example.net.MVPObserver;
import com.example.net.RetrofitCreator;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.shaomall.framework.base.BaseActivity;
import com.shaomall.framework.bean.UploadBean;
import com.shaomall.framework.manager.ActivityInstanceManager;
import com.shaomall.framework.manager.HandPortraitManager;
import com.shaomall.framework.manager.UserInfoManager;
import com.wyp.avatarstudio.AvatarStudio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class SettingActivity extends BaseActivity {
    private LinearLayout settingLinearSex;
    private TextView settingTextSex;
    private LinearLayout settingLinearDate;
    private TextView settingTextDate;
    private DatePicker settingDatePicker;
    private LinearLayout settingLinearName;
    private TextView settingTextChangeName;
    private SimpleDraweeView settingPhoto;
    private TextView settingYong;
    private LinearLayout settingLinearPhoto;
    private List<String> arr = new ArrayList<String>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ToolbarCustom mTcAppActivitySetting;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        mTcAppActivitySetting = findViewById(R.id.tc_app_activity_setting);

        settingLinearPhoto = findViewById(R.id.settingLinearphoto);
        settingLinearName = findViewById(R.id.settingLinearName);
        settingTextChangeName = findViewById(R.id.settingTextChangeName);
        settingDatePicker = findViewById(R.id.settingDatePicker);
        settingLinearSex = findViewById(R.id.settingLinearSex);
        settingTextSex = findViewById(R.id.settingTextsex);
        settingLinearDate = findViewById(R.id.settingLinearDate);
        settingTextDate = findViewById(R.id.settingTextDate);
        settingPhoto = findViewById(R.id.settingPhoto);
        settingYong = findViewById(R.id.settingyong);


        arr.add("男");
        arr.add("女");
        arr.add("保密");

    }

    @Override
    protected void initData() {
        //选择头像拍照裁剪
        settingLinearPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AvatarStudio.Builder builder = new AvatarStudio.Builder(SettingActivity.this);
                builder.setAspect(1, 1)
                        .setOutput(100, 100)
                        .needCrop(true)
                        .dimEnabled(true)
                        .setText("相机", "本地相册", "取消")
                        .setTextColor(Color.GREEN)
                        .show(new AvatarStudio.CallBack() {
                            @Override
                            public void callback(String uri) {
                                settingPhoto.setImageURI(Uri.fromFile(new File(uri)));
                                //把已经设置完成的头像上传到服务器
                                File file = new File(uri);

                                upLode(file);
                            }
                        });
            }
        });

        //下载文件
        HandPortraitManager instance = HandPortraitManager.getInstance();
        instance.downloadFileData();


        //退出按钮
        mTcAppActivitySetting.setLeftBackImageViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityInstanceManager.removeActivity(SettingActivity.this);
            }
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String name = extras.getString("name");
        String head = extras.getString("head");
        settingTextChangeName.setText(name);
        settingPhoto.setImageURI(head);
        settingYong.setText(name);

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


                toast(year + "  " + month + "  " + "  " + dayOfMonth, false);
            }
        });

    }

    private void upLode(File file) {
        if (!file.exists()) {
            toast("上传的文件不存在", false);
        }
        //创建上传文件的请求体
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        //创建上传文件的part参数
        MultipartBody.Part uploadPart = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        //进行网络请求
        RetrofitCreator.getNetApiService().upload(uploadPart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MVPObserver<ResponseBody>() {
                    private Disposable mDisposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        //订阅
                        mDisposable = d;
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        //判断mDisposable.isDisposed()如果解除了则不需要处理 true为解除
                        if (mDisposable.isDisposed()) {
                            return;
                        }

                        String str;
                        try {
                            str = responseBody.string();
                            UploadBean uploadBean = new Gson().fromJson(str, UploadBean.class);
                            if ("200".equals(uploadBean.getCode())) {
                                UserInfoManager.getInstance().upDataAvater(uploadBean.getResult());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    // 弹出选择器
    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                settingTextSex.setText(arr.get(options1));

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }
}
