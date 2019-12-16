package com.example.administrator.shaomall.type;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.AdapterView;

import com.example.administrator.shaomall.R;
import com.example.administrator.shaomall.type.adapter.TypeLeftAdapter;
import com.example.administrator.shaomall.type.adapter.TypeRightAdapter;
import com.example.commen.Constants;
import com.example.net.AppNetConfig;
import com.shaomall.framework.base.BaseMVPFragment;
import com.shaomall.framework.bean.TypeBean;

import java.util.List;

public class TypeFragment extends BaseMVPFragment<TypeBean> {
    private android.widget.ListView mTypeLeftLv;
    private android.support.v7.widget.RecyclerView mTypeRightRv;
    private TypePresenter typePresenter;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_type;
    }

    private String[] urls = new String[]{AppNetConfig.SKIRT_URL, AppNetConfig.JACKET_URL, AppNetConfig.PANTS_URL, AppNetConfig.OVERCOAT_URL,
            AppNetConfig.ACCESSORY_URL, AppNetConfig.BAG_URL, AppNetConfig.DRESS_UP_URL, AppNetConfig.HOME_PRODUCTS_URL, AppNetConfig.STATIONERY_URL,
            AppNetConfig.DIGIT_URL, AppNetConfig.GAME_URL};
    private TypeLeftAdapter typeLeftAdapter;
    private boolean isFirest = true;


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mTypeLeftLv = view.findViewById(R.id.type_left_lv);
        mTypeRightRv = view.findViewById(R.id.type_right_rv);
    }

    @Override
    protected void initData() {
        getDataFormNet(urls[0]);


    }

    private void initListener(final TypeLeftAdapter adapter) {
        //选中监听
        mTypeLeftLv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter.changeSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //点击监听
        mTypeLeftLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.changeSelected(position);
                if (position!=0)
                {
                    isFirest = false;
                }
                getDataFormNet(urls[position]);
                typeLeftAdapter.notifyDataSetChanged();
            }
        });
    }
    private void getDataFormNet(String url) {
        typePresenter = new TypePresenter();
        typePresenter.setPath(url);
        typePresenter.attachView(this);
        typePresenter.doGetHttpRequest(Constants.TYPE_DATA_CODE);

    }


    @Override
    public void onRequestHttpDataListSuccess(int requestCode, String message, List<TypeBean> data) {
        super.onRequestHttpDataListSuccess(requestCode, message, data);
        if (requestCode == Constants.TYPE_DATA_CODE) {
            if (isFirest)
            {
                typeLeftAdapter = new TypeLeftAdapter(getContext());
                mTypeLeftLv.setAdapter(typeLeftAdapter);
            }

            initListener(typeLeftAdapter);
            TypeRightAdapter typeRightAdapter = new TypeRightAdapter(getContext(), data);
            mTypeRightRv.setAdapter(typeRightAdapter);
            GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
            //spanCount即列数。这里GridLayoutManager的第二个参数就是spanCount
            //SpanSize为多少，表示占用几个item
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position == 0) {
                        return 3;
                    } else {
                        return 1;
                    }
                }
            });
            mTypeRightRv.setLayoutManager(manager);
        }

    }
}
