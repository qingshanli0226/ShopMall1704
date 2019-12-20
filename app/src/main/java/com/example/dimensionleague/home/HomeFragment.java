package com.example.dimensionleague.home;

import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.buy.activity.SearchActivity;
import com.example.common.HomeBean;
import com.example.common.utils.IntentUtil;
import com.example.common.code.Constant;
import com.example.common.view.MyToolBar;
import com.example.dimensionleague.CacheManager;
import com.example.dimensionleague.R;
import com.example.dimensionleague.home.adapter.HomeAdapter;
import com.example.framework.base.BaseNetConnectFragment;
import com.example.framework.port.AppBarStateChangeListener;
import com.example.point.message.MessageActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;



public class HomeFragment extends BaseNetConnectFragment {
    private RecyclerView rv;

    private HomeBean.ResultBean list=null;

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
        if(!isConnectStatus()){
            hideError();
            hideLoading();
            showEmpty();
        }else{
            hideEmpty();
        }
        rv = view.findViewById(R.id.home_rv);
        my_toolbar = view.findViewById(R.id.my_toolbar);
        appBarLayout = view.findViewById(R.id.mApp_layout);
        toolbar_layout = view.findViewById(R.id.toolbar_layout);
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
        HomeAdapter adapter = new HomeAdapter(list, getContext());
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
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
        my_toolbar.getHome_camera().setOnClickListener(v -> {
            int permission = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                permission = getContext().checkSelfPermission(Manifest.permission.CAMERA);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, Constant.REQUSET_CODE);
                    return;
                } else {
                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, Constant.REQUSET_CAMERA_CODE);
                }
            }
        });
        //TODO 跳转搜索页面

        my_toolbar.getHome_search().setOnClickListener(v -> startActivity(SearchActivity.class,null));
        my_toolbar.getHome_message().setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(IntentUtil.LOGIN,"消息");
            startActivity(MessageActivity.class,bundle);
        });
    }

    //    点击事件
    private void toolbarMessenger() {
        my_toolbar.getHome_search_back().setOnClickListener(v -> {
            int permission = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                permission = getContext().checkSelfPermission(Manifest.permission.CAMERA);

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
                    // Permission Granted 用户允许权限 继续执行（检查的是照相机权限）
                    Intent intent = new Intent(getActivity(), CaptureActivity.class);
                    startActivityForResult(intent, Constant.REQUSET_ZXING_CODE);

                } else {
                    // Permission Denied 拒绝
                    toast(getActivity(), getString(R.string.request_no));
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
        return R.id.home_relativeLayout;
    }

    @Override
    public void onRequestSuccess(Object data) {

        if (String.valueOf(((HomeBean) data).getCode()) == Constant.CODE_OK) {
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
                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        String result = bundle.getString(CodeUtils.RESULT_STRING);
                        toast(getActivity(), result);
                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                        toast(getActivity(), getString(R.string.failure));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else if (requestCode==Constant.REQUSET_CAMERA_CODE){
            if (data!=null){
                toast(getActivity(),getString(R.string.camera_ok));
            }else{
                toast(getActivity(),getString(R.string.camera_no));
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

}