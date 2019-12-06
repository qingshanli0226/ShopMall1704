package com.example.dimensionleague.home;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dimensionleague.CacheManager;
import com.example.dimensionleague.R;
import com.example.dimensionleague.businessbean.HomeBean;
import com.example.dimensionleague.home.adapter.HomeAdapter;
import com.example.framework.base.BaseNetConnectFragment;
import com.example.framework.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseNetConnectFragment {
    private RecyclerView rv;
    private HomeAdapter adapter;
    private HomeBean.ResultBean list;
    private  BasePresenter iBasePresenter;


    @Override
    public void init(View view) {
        super.init(view);
        rv=view.findViewById(R.id.home_rv);
        iBasePresenter=new HomePresenter();
    }

    @Override
    public void initDate() {
//        iBasePresenter.attachView(this);
//        iBasePresenter.onHttpGetRequest();
        Log.d("sss", "initDate: "+((HomeBean) CacheManager.getInstance().getHomeBeanData()).result);
        list=(((HomeBean) CacheManager.getInstance().getHomeBeanData()).result );
        adapter=new HomeAdapter(list,getContext());
// 设置网格布局
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        rv.setAdapter(adapter);

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
        if (data!=null){
            adapter=new HomeAdapter(((HomeBean)data).result,getContext());
// 设置网格布局
            rv.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            rv.setAdapter(adapter);
        }else{
        }
    }
}
