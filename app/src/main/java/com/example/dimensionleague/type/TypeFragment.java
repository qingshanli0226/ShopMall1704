package com.example.dimensionleague.type;

import android.app.Activity;
import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dimensionleague.search.SearchActivity;
import com.example.common.TypeBean;
import com.example.common.code.Constant;
import com.example.common.view.MyToolBar;
import com.example.dimensionleague.R;
import com.example.framework.base.BaseNetConnectFragment;
import com.example.net.AppNetConfig;
import com.example.point.message.MessageActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TypeFragment extends BaseNetConnectFragment {
   private Activity activity;

    public TypeFragment(Activity activity) {
        this.activity = activity;
    }

    private RecyclerView rv_right;
    private ListView type_left;
    private TypePresenter typePresenter;
    private TypeTagAdapter tagAdapter;
    private List<String> typeURL;
    private MyToolBar my_toolbar;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_type;
    }

    @Override
    public void init(View view) {
        super.init(view);
        type_left = view.findViewById(R.id.type_lv_left);
        rv_right = view.findViewById(R.id.type_rv_right);
        my_toolbar = view.findViewById(R.id.my_toolbar);
        //TODO 设置ToolBar的风格
        my_toolbar.init(Constant.SEARCH_STYLE);
        my_toolbar.setBackground(getResources().getDrawable(R.drawable.toolbar_style));
        typePresenter = new TypePresenter();
        typeURL = new ArrayList<>();
    }

    @Override
    public void initDate() {
        showMyToolBar();
        //TODO 扫一扫
        my_toolbar.getScan().setOnClickListener(v -> {
            final RxPermissions rxPermissions = new RxPermissions(activity);
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
        //TODO AI相机
        my_toolbar.getSearch_camera().setOnClickListener(v -> {

        });
        //TODO 点击搜索跳转到搜索页面
        my_toolbar.getSearch_edit().setFocusable(false);
        my_toolbar.getSearch_edit().clearFocus();
        my_toolbar.getSearch_edit().setOnClickListener(v -> {
                startActivity(SearchActivity.class, null);
                v.clearFocus();
        });
        //TODO 点击消息跳转到消息页面
        my_toolbar.getSearch_message().setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("intent", "消息");
            startActivity(MessageActivity.class, bundle);
        });
        typeURL.add(AppNetConfig.SKIRT_URL);
        typeURL.add(AppNetConfig.JACKET_URL);
        typeURL.add(AppNetConfig.PANTS_URL);
        typeURL.add(AppNetConfig.OVERCOAT_URL);
        typeURL.add(AppNetConfig.ACCESSORY_URL);
        typeURL.add(AppNetConfig.BAG_URL);
        typeURL.add(AppNetConfig.DRESS_UP_URL);
        typeURL.add(AppNetConfig.HOME_PRODUCTS_URL);
        typeURL.add(AppNetConfig.STATIONERY_URL);
        typeURL.add(AppNetConfig.DIGIT_URL);
        typeURL.add(AppNetConfig.GAME_URL);

//        第一个数据
        typePresenter.attachView(this);
        typePresenter.setURL(typeURL.get(0));
        typePresenter.doHttpGetRequest(1);
        tagAdapter = new TypeTagAdapter(Objects.requireNonNull(getContext()));
        type_left.setAdapter(tagAdapter);
        rv_right.setLayoutManager(new LinearLayoutManager(getContext()));
//监听事件
        type_left.setOnItemClickListener((parent, view, position, id) -> {
            tagAdapter.changeSelected(position);
            typePresenter.setURL(typeURL.get(position));
            typePresenter.doHttpGetRequest(1);
            tagAdapter.notifyDataSetChanged();


        });
        type_left.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tagAdapter.changeSelected(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showMyToolBar() {
        my_toolbar.getScan().setVisibility(View.VISIBLE);
        my_toolbar.getScan().setImageResource(R.drawable.scan_code);
        my_toolbar.getSearch_message().setVisibility(View.VISIBLE);
        my_toolbar.getSearch_text().setVisibility(View.GONE);
    }

    @Override
    public void onRequestSuccess(int requestCode, Object data) {
        if (requestCode == 1) {
            List<TypeBean.ResultBean> result = ((TypeBean) data).getResult();
            TypeRightAdapter rightAdapter = new TypeRightAdapter(getContext(), result);
            rv_right.setAdapter(rightAdapter);
        }

    }

    @Override
    public int getRelativeLayout() {
        return R.id.type_Relative;
    }

    @Override
    public void onConnected() {
        hideEmpty();
    }

    @Override
    public void onDisConnected() {
        hideError();
        hideLoading();
        showEmpty();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (typePresenter != null) {
            typePresenter.detachView();
        }
        typePresenter = null;
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
        } else if (requestCode == Constant.REQUSET_CAMERA_CODE) {
            if (data != null) {
                toast(getActivity(), getString(R.string.camera_ok));
            } else {
                toast(getActivity(), getString(R.string.camera_no));
            }
        }
    }
}
