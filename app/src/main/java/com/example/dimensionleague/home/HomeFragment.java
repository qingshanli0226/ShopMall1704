package com.example.dimensionleague.home;

import android.app.Activity;
import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dimensionleague.search.SearchActivity;
import com.example.common.HomeBean;
import com.example.common.port.IAccountCallBack;
import com.example.common.utils.IntentUtil;
import com.example.common.code.Constant;
import com.example.common.view.MyToolBar;
import com.example.dimensionleague.CacheManager;
import com.example.dimensionleague.R;
import com.example.dimensionleague.home.adapter.HomeAdapter;
import com.example.dimensionleague.login.activity.LoginActivity;
import com.example.dimensionleague.setting.SettingActivity;
import com.example.framework.base.BaseNetConnectFragment;
import com.example.framework.manager.AccountManager;
import com.example.framework.port.AppBarStateChangeListener;
import com.example.net.AppNetConfig;
import com.example.point.message.MessageActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class HomeFragment extends BaseNetConnectFragment implements IAccountCallBack {
    private Activity activity;

    public HomeFragment(Activity activity) {
        this.activity = activity;
    }

    private RecyclerView rv;
    private HomeBean.ResultBean list = null;

    private int type = 0;
    private MyToolBar my_toolbar;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout toolbar_layout;
    private ImageView home_icon;

    private ImageView home_image;

    boolean flag_head = true;

    public HomeFragment(int i) {
        super();
        this.type = i;
    }

    @Override
    public void init(View view) {
        super.init(view);
        if (!isConnectStatus()) {
            hideError();
            hideLoading();
            showEmpty();
        } else {
            hideEmpty();
        }
        AccountManager.getInstance().registerUserCallBack(this);

        rv = view.findViewById(R.id.home_rv);
        my_toolbar = view.findViewById(R.id.my_toolbar);
        appBarLayout = view.findViewById(R.id.mApp_layout);
        toolbar_layout = view.findViewById(R.id.toolbar_layout);
        home_icon = view.findViewById(R.id.home_icon);

//        判断type值来显示隐藏搜索框
        if (type == 1) {
            appBarLayout.setVisibility(View.GONE);
        }

        home_image = view.findViewById(R.id.home_icon);
        if (!AccountManager.getInstance().isLogin()) {
            Glide.with(getContext()).load(R.drawable.default_head_image).apply(new RequestOptions().circleCrop()).into(home_image);
        } else {
            if (AccountManager.getInstance().getUser() == null) {
                AccountManager.getInstance().logout();
                Toast.makeText(getContext(), getResources().getString(R.string.timeout), Toast.LENGTH_SHORT).show();
                Glide.with(getContext()).load(R.drawable.default_head_image).apply(new RequestOptions().circleCrop()).into(home_image);
            } else {
                if (AccountManager.getInstance().getUser().getAvatar() == null) {
                    Glide.with(getContext()).load(R.drawable.default_head_image).apply(new RequestOptions().circleCrop()).into(home_image);
                } else {
                    Glide.with(getContext()).load(AppNetConfig.BASE_URL + AccountManager.getInstance().getUser().getAvatar()).apply(new RequestOptions().circleCrop()).into(home_image);
                }
            }
        }
        home_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AccountManager.getInstance().isLogin()) {
                    startActivity(LoginActivity.class, null);
                } else {
                    startActivity(SettingActivity.class, null);
                }
            }
        });
    }

    @Override
    public void initDate() {

        if (CacheManager.getInstance().getHomeBean() != null) {
            list = ((CacheManager.getInstance().getHomeBean()).getResult());
        }
        HomeAdapter adapter = new HomeAdapter(list, getContext());

        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) { //TODO 展开状态
                    //TODO 展开后分发事件
                    my_toolbar.setVisibility(View.GONE);
                    my_toolbar.GoneAll();
                } else if (state == State.COLLAPSED) {//TODO 折叠状态
                    //TODO 当折叠后拦截事件
                    my_toolbar.setVisibility(View.VISIBLE);
                    my_toolbar.init(Constant.HOME_STYLE);
//                    扫一扫及消息的点击事件
                    toolbarMessenger();
                }
            }
        });

        toolbar_layout.setContentScrim(getResources().getDrawable(R.drawable.toolbar_style));
        //TODO 跳转相机
        my_toolbar.getHome_camera().setOnClickListener(v -> {

        });
        //TODO 跳转搜索页面
        my_toolbar.getHome_search().setOnClickListener(v -> startActivity(SearchActivity.class, null));
        my_toolbar.getHome_message().setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(IntentUtil.LOGIN, "消息");
            startActivity(MessageActivity.class, bundle);
        });
    }

    // 扫一扫
    private void toolbarMessenger() {
        my_toolbar.getHome_search_back().setOnClickListener(v -> {
            RxPermissions rxPermissions = new RxPermissions(activity);
            rxPermissions.request(
                    Manifest.permission.CAMERA)
                    .subscribe(granted -> {
                        if (granted) {
                            Intent intent = new Intent();
                            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, Constant.REQUSET_CAMERA_CODE);
                        }
                    });
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AccountManager.getInstance().unRegisterUserCallBack(this);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public int getRelativeLayout() {
        return R.id.home_relativeLayout;
    }

    @Override
    public void onRequestSuccess(Object data) {

        if (String.valueOf(((HomeBean) data).getCode()).equals(Constant.CODE_OK)) {
            list = ((HomeBean) data).getResult();
            rv.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUSET_ZXING_CODE) {
            if (data != null) {
                try {
                    Bundle bundle = data.getExtras();
                    if (Objects.requireNonNull(bundle).getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        String result = bundle.getString(CodeUtils.RESULT_STRING);
                        toast(getActivity(), result);
                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                        toast(getActivity(), getString(R.string.failure));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == Constant.REQUSET_CAMERA_CODE) {
            if (data != null) {
                toast(getActivity(), getString(R.string.camera_ok));
            } else {
                toast(getActivity(), getString(R.string.camera_no));
            }
        }
    }

    @Override
    public void onConnected() {
        Toast.makeText(getContext(), "有网络了", Toast.LENGTH_SHORT).show();
        hideEmpty();
    }

    @Override
    public void onDisConnected() {
        Toast.makeText(getContext(), "没网络了", Toast.LENGTH_SHORT).show();
        hideError();
        hideLoading();
        showEmpty();
    }

    @Override
    public void onRegisterSuccess() {

    }

    @Override
    public void onLogin() {
    }

    @Override
    public void onLogout() {
        Glide.with(getContext()).load(R.drawable.default_head_image).apply(new RequestOptions().circleCrop()).into(home_image);
    }

    @Override
    public void onAvatarUpdate(String url) {
        Glide.with(getContext()).load(url).apply(new RequestOptions().circleCrop()).into(home_image);
    }
}