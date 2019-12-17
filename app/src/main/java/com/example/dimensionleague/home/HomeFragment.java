package com.example.dimensionleague.home;

import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buy.activity.SearchActivity;
import com.example.common.HomeBean;
import com.example.common.code.Constant;
import com.example.common.view.MyToolBar;
import com.example.dimensionleague.CacheManager;
import com.example.dimensionleague.R;
import com.example.dimensionleague.home.adapter.HomeAdapter;
import com.example.framework.base.BaseNetConnectFragment;
import com.example.framework.port.IPresenter;
import com.example.framework.port.AppBarStateChangeListener;
import com.example.point.message.MessageActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;


public class HomeFragment extends BaseNetConnectFragment {
    private RecyclerView rv;
    private HomeAdapter adapter;

    private HomeBean.ResultBean list = new HomeBean.ResultBean();
    private IPresenter homePresnter;

    private int type = 0;
    private MyToolBar my_toolbar;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout toolbar_layout;


    public HomeFragment(int i) {
        super();
        this.type = i;
    }

    public HomeFragment() {
        super();
    }

    @Override
    public void init(View view) {
        super.init(view);
        rv = view.findViewById(R.id.home_rv);
        my_toolbar = view.findViewById(R.id.my_toolbar);
        appBarLayout = view.findViewById(R.id.mApp_layout);
        toolbar_layout = view.findViewById(R.id.toolbar_layout);
        homePresnter = new HomePresenter();
//        判断type值来显示隐藏搜索框
        if (type == 1) {
            appBarLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void initDate() {

        if (CacheManager.getInstance().getHomeBeanData() != null) {
            list = (((HomeBean) CacheManager.getInstance().getHomeBeanData()).getResult());
        }
        adapter = new HomeAdapter(list, getContext());
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Log.d("lhfff", "onStateChanged: " + state.name());
                if (state == State.EXPANDED) { //TODO 展开状态
                    my_toolbar.GoneAll();
                } else if (state == State.COLLAPSED) {//TODO 折叠状态
                    my_toolbar.init(Constant.HOME_STYLE);
//                    扫一扫及消息的点击事件
                    toolbarMessenger();
                } else {  //TODO 中间状态

                }
            }
        });

        toolbar_layout.setContentScrim(getResources().getDrawable(R.drawable.toolbar_style));
        //TODO 跳转相机
        my_toolbar.getHome_camera().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "跳转到相机", Toast.LENGTH_SHORT).show();
            }
        });
        //TODO 跳转搜索页面
        my_toolbar.getHome_search().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
        my_toolbar.getHome_message().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent);
            }
        });
    }

    //    点击事件
    private void toolbarMessenger() {
        my_toolbar.getHome_search_back().setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                int permission = getContext().checkSelfPermission(Manifest.permission.CAMERA);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, Constant.REQUSET_CODE);
                    return;
                } else {
                    Intent intent = new Intent(getActivity(), CaptureActivity.class);
                    startActivityForResult(intent, Constant.REQUSET_ZXING_CODE);
                }

            }
        });
    }


//    处理权限

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constant.REQUSET_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted 用户允许权限 继续执行（我这里执行的是二维码扫描，检查的是照相机权限）
                    Intent intent = new Intent(getActivity(), CaptureActivity.class);
                    startActivityForResult(intent, Constant.REQUSET_ZXING_CODE);

                } else {
                    // Permission Denied 拒绝
                    toast(getActivity(), "获取权限失败，无法扫描");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public int getRelativeLayout() {
        return 0;
    }

    @Override
    public void onRequestSuccess(Object data) {

        if (((HomeBean) data).getCode() == 200) {
            list = ((HomeBean) data).getResult();
            rv.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("xxxxx", "onActivityResult: ");
        if (requestCode == Constant.REQUSET_ZXING_CODE) {
            if (data != null) {
                try {
                    Bundle bundle = data.getExtras();
                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        String result = bundle.getString(CodeUtils.RESULT_STRING);
                        toast(getActivity(), result);
                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                        toast(getActivity(), "解析二维码失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}