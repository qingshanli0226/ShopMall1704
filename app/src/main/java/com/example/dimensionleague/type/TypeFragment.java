package com.example.dimensionleague.type;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dimensionleague.R;
import com.example.common.TypeBean;
import com.example.framework.base.BaseNetConnectFragment;
import com.example.net.AppNetConfig;

import java.util.ArrayList;
import java.util.List;

public class TypeFragment extends BaseNetConnectFragment {
    private RecyclerView rv_right;
    private ListView type_left;
    private TypePresenter typePresenter;
    private TypeTagAdapter tagAdapter;
    private List<String> typeURL;

    private TypeRightAdapter rightAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_type;
    }

    @Override
    public void init(View view) {
        super.init(view);
        type_left=view.findViewById(R.id.type_lv_left);
        rv_right=view.findViewById(R.id.type_rv_right);
        typePresenter=new TypePresenter();
        typeURL=new ArrayList<>();
    }

    @Override
    public void initDate() {
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
        tagAdapter=new TypeTagAdapter(getContext());
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

    @Override
    public void onRequestSuccess(int requestCode, Object data) {
        switch (requestCode){
            case 1:
                List<TypeBean.ResultBean> result = ((TypeBean) data).getResult();
                rightAdapter=new TypeRightAdapter(getContext(),result);
                rv_right.setAdapter(rightAdapter);
            break;
        }

    }

    @Override
    public int getRelativeLayout() {
        return R.id.type_Relative;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (typePresenter!=null){
            typePresenter.detachView();
        }
        typePresenter=null;
    }

    @Override
    public void showLoading() {

    }
}
