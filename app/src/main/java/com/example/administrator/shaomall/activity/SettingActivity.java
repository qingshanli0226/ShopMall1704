package com.example.administrator.shaomall.activity;

import android.accounts.AccountManager;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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
import com.example.net.AppNetConfig;
import com.example.net.MVPObserver;
import com.example.net.RetrofitCreator;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.shaomall.framework.base.BaseActivity;
import com.shaomall.framework.bean.LoginBean;
import com.shaomall.framework.bean.UploadBean;
import com.shaomall.framework.manager.HandPortraitManager;
import com.shaomall.framework.manager.UserInfoManager;
import com.wyp.avatarstudio.AvatarStudio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

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
    private LinearLayout settingLinearphoto;



    ArrayList<String> arr=new ArrayList<String>();
    @Override
    protected int setLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        settingLinearphoto = (LinearLayout) findViewById(R.id.settingLinearphoto);
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
        //选择头像拍照裁剪
        settingLinearphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AvatarStudio.Builder builder = new AvatarStudio.Builder(SettingActivity.this);
                builder.setAspect(1,1)
                        .setOutput(100,100)
                        .needCrop(true)
                        .dimEnabled(true)
                        .setText("相机","本地相册","取消")
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

    private void upLode(File file) {
        if (!file.exists()){
            Toast.makeText(mActivity, "上传的文件不存在", Toast.LENGTH_SHORT).show();
        }
        //创建上传文件的请求体
        RequestBody requestBody=RequestBody.create(MediaType.parse("image/*"),file);
        //创建上传文件的part参数
        MultipartBody.Part uploadPart = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        //进行网络请求
        RetrofitCreator.getNetApiService().upload(uploadPart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MVPObserver<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        String s=null;
                        try {
                            s=responseBody.string();
                            UploadBean uploadBean=new Gson().fromJson(s,UploadBean.class);

                            if ("200".equals(uploadBean.getCode())) {
                                LoginBean loginBean = UserInfoManager.getInstance().upDataAvater(uploadBean.getResult());
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
