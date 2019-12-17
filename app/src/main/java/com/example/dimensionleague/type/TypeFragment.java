package com.example.dimensionleague.type;

import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.common.TypeBean;
import com.example.common.code.Constant;
import com.example.common.view.MyToolBar;
import com.example.dimensionleague.R;
import com.example.framework.base.BaseNetConnectFragment;
import com.example.net.AppNetConfig;
import com.example.point.message.MessageActivity;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;


import java.util.ArrayList;
import java.util.List;

public class TypeFragment extends BaseNetConnectFragment {
    private RecyclerView rv_right;
    private ListView type_left;
    private TypePresenter typePresenter;
    private TypeTagAdapter tagAdapter;
    private List<String> typeURL;
    private TypeRightAdapter rightAdapter;
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
        my_toolbar.getScan().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
        //TODO AI相机
        my_toolbar.getSearch_camera().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "拍照搜索商品", Toast.LENGTH_SHORT).show();
            }
        });
        //TODO 点击搜索跳转到搜索页面
        my_toolbar.getSearch_edit().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"跳转搜索页面",Toast.LENGTH_SHORT).show();
            }
        });
        //TODO 点击消息跳转到消息页面
        my_toolbar.getSearch_message().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent);
            }
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
        tagAdapter = new TypeTagAdapter(getContext());
        type_left.setAdapter(tagAdapter);
        rv_right.setLayoutManager(new LinearLayoutManager(getContext()));
//监听事件
        type_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tagAdapter.changeSelected(position);
                typePresenter.setURL(typeURL.get(position));
                typePresenter.doHttpGetRequest(1);
                tagAdapter.notifyDataSetChanged();


            }
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
        switch (requestCode) {
            case 1:
                List<TypeBean.ResultBean> result = ((TypeBean) data).getResult();
                rightAdapter = new TypeRightAdapter(getContext(), result);
                rv_right.setAdapter(rightAdapter);
                break;
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
                        toast(getActivity(), "解析二维码失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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
    public void showLoading() {
    }
}
