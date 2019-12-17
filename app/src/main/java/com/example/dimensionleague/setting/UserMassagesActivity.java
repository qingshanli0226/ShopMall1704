package com.example.dimensionleague.setting;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.common.port.IAccountCallBack;
import com.example.dimensionleague.R;
import com.example.dimensionleague.userbean.UploadBean;
import com.example.framework.base.BaseActivity;
import com.example.framework.manager.AccountManager;
import com.example.framework.manager.ErrorDisposeManager;
import com.example.net.AppNetConfig;
import com.example.net.RetrofitCreator;
import com.google.gson.Gson;
import com.wyp.avatarstudio.AvatarStudio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class UserMassagesActivity extends BaseActivity implements IAccountCallBack {
    private List<SettingBean> list;
    private String[] sexArry = new String[]{"保密", "女", "男"};// 性别选择
    private RecyclerView rv;
    private ImageView heanUserImg;
    private TextView heanUserName;
    private View headView;
    private Calendar nowdate=Calendar.getInstance();
    private int mYear;
    private int mMonth;
    private int mDay;
    private MySettingAdapter adapter;

    @Override
    public void onRegisterSuccess() {}

    @Override
    public void onLogin() {}

    @Override
    public void onLogout() {
    }

    @Override
    public void onAvatarUpdate(String url) {
        Glide.with(this).load(AccountManager.getInstance().user.getAvatar()).apply(new RequestOptions().circleCrop()).into(heanUserImg);
    }

    @Override
    public int getLayoutId() {
       return R.layout.activity_user_massage;
    }

    @Override
    public void init() {

        headView = LayoutInflater.from(this).inflate(R.layout.user_item_head, null);
        heanUserImg=headView.findViewById(R.id.user_massage_item_img) ;
        heanUserName=headView.findViewById(R.id.user_massage_item_title);
        AccountManager.getInstance().registerUserCallBack(this);
        rv=findViewById(R.id.user_massage_rv);
    }

    @Override
    public void initDate() {
        list=new ArrayList<>();
        list.add(new SettingBean("用户名",""));
        list.add(new SettingBean("昵称",""));
        list.add(new SettingBean("性别","保密"));
        list.add(new SettingBean("出生年月",""));
        mYear =nowdate.get(Calendar.YEAR);
        mMonth = nowdate.get(Calendar.MONTH);
        mDay = nowdate.get(Calendar.DAY_OF_MONTH);
//         判断是否登录
        if (AccountManager.getInstance().isLogin()){
            list.get(0).setMassage(""+AccountManager.getInstance().user.getName());
            list.get(1).setMassage(""+AccountManager.getInstance().user.getName());
            if (AccountManager.getInstance().user.getAvatar()!=null){
                Glide.with(this).load(""+AppNetConfig.BASE_URL+AccountManager.getInstance().user.getAvatar()).apply(new RequestOptions().circleCrop()).into(heanUserImg);
            }
        }
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter= new MySettingAdapter(R.layout.setting_item,list);
        adapter.addHeaderView(headView);
        rv.setAdapter(adapter);
        AccountManager.getInstance().registerUserCallBack(this);
//        点击事件
        initListener();



    }

    private void initListener() {
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AvatarStudio.Builder(UserMassagesActivity.this)
                        .needCrop(false)
                        .dimEnabled(true)
                        .setAspect(1,1)
                        .setOutput(50,50)
                        .setText("拍照","我的相册","取消")
                        .setTextColor(Color.BLUE)
                        .show(new AvatarStudio.CallBack() {
                            @Override
                            public void callback(String uri) {
                                File file = new File(uri);
                                upload(file);
                            }
                        });

            }
        });


        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                switch (position){
                    case 0:
                        Toast toast = Toast.makeText(view.getContext(), "这个是不可以修改的哦`", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                        break;
                    case 1:
                        View inflate = LayoutInflater.from(UserMassagesActivity.this).inflate(R.layout.user_item_set_name, null);
                        AlertDialog.Builder builder = new AlertDialog.Builder(UserMassagesActivity.this)
                                .setView(inflate)
                                .setCancelable(false)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        EditText et = inflate.findViewById(R.id.user_changename_edit);
                                        list.get(position).setMassage("" + et.getText().toString());
                                        adapter.notifyDataSetChanged();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                builder.show();
                        break;
                    case 2:
                        showSexDialog(position);
                        break;
                    case 3:
                        new DatePickerDialog(UserMassagesActivity.this, DatePickerDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String s = new StringBuffer().append(mYear).append("年").append(mMonth)
                                        .append("月")
                                        .append(mDay).append("日").toString();
                                list.get(position).setMassage("" + s);
                                adapter.notifyDataSetChanged();
                            }
                        },mYear, mMonth, mDay).show();
                        break;
                }
            }
        });
            }

    private void showSexDialog(int position) {
//        性别选择
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(sexArry, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                list.get(position).setMassage(sexArry[which]);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        builder.show();

    }


//上传头像
    private void upload(File file) {
        if (!file.exists()) {
            toast(this,"上传文件不存在!");
            return;
        }
        //创建上传文件的请求体.
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        //创建上传文件的part参数.
        MultipartBody.Part uploadPart = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RetrofitCreator.getNetInterence().upload(uploadPart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        String s = null;
                        try {
                            s = responseBody.string();
                            Log.i("xxxx", "ResponseBody:上传 "+s);
                            UploadBean uploadBean = new Gson().fromJson(s, UploadBean.class);
                            if ("200".equals(uploadBean.getCode())){
                                AccountManager.getInstance().getUser().setAvatar(uploadBean.getResult());
                                AccountManager.getInstance().notifyUserAvatarUpdate(AccountManager.getInstance().getUser().getAvatar().toString());
                            }
                        } catch (IOException e) {
                            ErrorDisposeManager.HandlerError(e);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("xxxx", "onError:cw上传 ");
                        ErrorDisposeManager.HandlerError(e);
                    }

                    @Override
                    public void onComplete() {}
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AccountManager.getInstance().unRegisterUserCallBack(this);
    }
}
