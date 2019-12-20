package com.example.dimensionleague.setting;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.common.code.Constant;
import com.example.common.port.IAccountCallBack;
import com.example.common.view.MyToolBar;
import com.example.dimensionleague.R;
import com.example.dimensionleague.userbean.UploadBean;
import com.example.framework.base.BaseActivity;
import com.example.framework.manager.AccountManager;
import com.example.net.AppNetConfig;
import com.example.net.RetrofitCreator;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
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

@SuppressWarnings({"ALL", "ResultOfMethodCallIgnored"})
public class UserMassagesActivity extends BaseActivity implements IAccountCallBack {
    private List<SettingBean> list;
    private String[] sexArry;
    private RecyclerView rv;
    private ImageView heanUserImg;
    private TextView heanUserName;
    private View headView;
    private final Calendar nowdate = Calendar.getInstance();
    private int mYear;
    private int mMonth;
    private int mDay;
    private MySettingAdapter adapter;
    private MyToolBar user_toolbar;

    @Override
    public void onRegisterSuccess() {
    }

    @Override
    public void onLogin() {
    }

    @Override
    public void onLogout() {
    }

    @Override
    public void onAvatarUpdate(String url) {
        Glide.with(this).load(AppNetConfig.BASE_URL + AccountManager.getInstance().user.getAvatar()).apply(new RequestOptions().circleCrop()).into(heanUserImg);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_massage;
    }

    @Override
    @SuppressLint("InflateParams")
    public void init() {
        user_toolbar = findViewById(R.id.user_toolbar);
        user_toolbar.init(Constant.OTHER_STYLE);
        user_toolbar.setBackgroundColor(Color.WHITE);
        user_toolbar.getOther_back().setImageResource(R.drawable.back2);
        user_toolbar.getOther_title().setText("个人设置");
        user_toolbar.getOther_title().setTextColor(Color.BLACK);
        headView = LayoutInflater.from(this).inflate(R.layout.user_item_head,null);

        heanUserImg = headView.findViewById(R.id.user_massage_item_img);
        heanUserName = headView.findViewById(R.id.user_massage_item_title);

        AccountManager.getInstance().registerUserCallBack(this);
        rv = findViewById(R.id.user_massage_rv);
    }

    @Override
    public void initDate() {
        list=new ArrayList<>();
        sexArry = new String[]{
                getString(R.string.privary),
                getString(R.string.woman),
                getString(R.string.man)
        };
        list.add(new SettingBean(getString(R.string.setting_user),""));
        list.add(new SettingBean(getString(R.string.name),""));
        list.add(new SettingBean(getString(R.string.sex),getString(R.string.privary)));
        list.add(new SettingBean(getString(R.string.birth),""));

        mYear = nowdate.get(Calendar.YEAR);
        mMonth = nowdate.get(Calendar.MONTH);
        mDay = nowdate.get(Calendar.DAY_OF_MONTH);
        user_toolbar.getOther_back().setOnClickListener(v -> finishActivity());
     //         判断是否登录
        if (AccountManager.getInstance().isLogin()) {
            if (AccountManager.getInstance().getUser().getName() != null) {
                list.get(0).setMassage("" + AccountManager.getInstance().user.getName());
                list.get(1).setMassage("" + AccountManager.getInstance().user.getName());
                if (AccountManager.getInstance().user.getAvatar() != null) {
                    Glide.with(this).load("" + AppNetConfig.BASE_URL + AccountManager.getInstance().user.getAvatar()).apply(new RequestOptions().circleCrop()).into(heanUserImg);
                }
            }
        }
        heanUserName.setText(getResources().getString(R.string.hean_user_name));
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MySettingAdapter(R.layout.setting_item, list);
        adapter.addHeaderView(headView);
        rv.setAdapter(adapter);
        AccountManager.getInstance().registerUserCallBack(this);
//        点击事件
        initListener();
    }

    private void initListener() {
        headView.setOnClickListener(v -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                int permission = this.checkSelfPermission(Manifest.permission.CAMERA);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, Constant.REQUSET_CODE);
                } else {
                    new AvatarStudio.Builder(UserMassagesActivity.this)
                            .needCrop(true)
                            .dimEnabled(true)
                            .setAspect(1, 1)
                            .setOutput(50, 50)
                            .setText(getString(R.string.camera), getString(R.string.albums), getString(R.string.cancel))
                            .setTextColor(Color.BLUE)
                            .show(uri -> {
                                File file = new File(uri);
                                upload(file);
                            });
                }
            }


        });
        adapter.setOnItemClickListener((adapter, view, position) -> {

            switch (position) {
                case 0:
                    Toast toast = Toast.makeText(view.getContext(), R.string.no, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    break;
                case 1:
                    @SuppressLint("InflateParams")
                    View inflate = LayoutInflater.from(UserMassagesActivity.this).inflate(R.layout.user_item_set_name, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserMassagesActivity.this)
                            .setView(inflate)
                            .setCancelable(false)
                            .setPositiveButton(R.string.yes, (dialog, which) -> {
                                EditText et = inflate.findViewById(R.id.user_changename_edit);
                                list.get(position).setMassage("" + et.getText().toString());
                                adapter.notifyDataSetChanged();
                            })
                            .setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel());
                    builder.show();
                    break;
                case 2:
                    showSexDialog(position);
                    break;
                case 3:
                    new DatePickerDialog(UserMassagesActivity.this, DatePickerDialog.THEME_HOLO_LIGHT, (view1, year, month, dayOfMonth) -> {
                        String s = mYear + getString(R.string.year) + mMonth +
                                getString(R.string.moth) +
                                mDay + getString(R.string.day);
                        list.get(position).setMassage("" + s);
                        adapter.notifyDataSetChanged();
                    }, mYear, mMonth, mDay).show();
                    break;

            }
        });
    }





        private void showSexDialog(int position) {
//        性别选择
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(sexArry, 0, (dialog, which) -> {
            list.get(position).setMassage(sexArry[which]);
            adapter.notifyDataSetChanged();
            dialog.dismiss();
        });
        builder.show();

    }


    //上传头像
    private void upload(File file) {
        if (!file.exists()) {
            toast(this,getString(R.string.up_file_no));
            return;
        }
        //创建上传文件的请求体.
        //noinspection deprecation
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        //创建上传文件的part参数.
        MultipartBody.Part uploadPart = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RetrofitCreator.getNetInterence().upload(uploadPart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @SuppressWarnings("ResultOfMethodCallIgnored")
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        String s;

                        try {
                            s = responseBody.string();
                            UploadBean uploadBean = new Gson().fromJson(s, UploadBean.class);
                            if (Constant.CODE_OK.equals(uploadBean.getCode())) {
                                AccountManager.getInstance().getUser().setAvatar(uploadBean.getResult());
                                AccountManager.getInstance().notifyUserAvatarUpdate(AccountManager.getInstance().getUser().getAvatar().toString());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
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

    //    处理权限

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constant.REQUSET_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new AvatarStudio.Builder(UserMassagesActivity.this)
                        .needCrop(true)
                        .dimEnabled(true)
                        .setAspect(1, 1)
                        .setOutput(50, 50)
                        .setText(getString(R.string.camera), getString(R.string.albums), getString(R.string.cancel))
                        .setTextColor(Color.BLUE)
                        .show(uri -> {
                            File file = new File(uri);
                            upload(file);
                        });

            } else {
                // Permission Denied 拒绝
                toast(this, getString(R.string.request_no));
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
