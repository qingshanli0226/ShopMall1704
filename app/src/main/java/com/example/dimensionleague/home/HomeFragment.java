package com.example.dimensionleague.home;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dimensionleague.CacheManager;
import com.example.dimensionleague.R;
import com.example.common.HomeBean;
import com.example.dimensionleague.home.adapter.HomeAdapter;
import com.example.framework.base.BaseNetConnectFragment;
import com.example.framework.base.BasePresenter;

public class HomeFragment extends BaseNetConnectFragment {
    private RecyclerView rv;
    private HomeAdapter adapter;
    private HomeBean.ResultBean list =new HomeBean.ResultBean();;
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
//        iBasePresenter.doHttpGetRequest();
//        Log.e("SSS",CacheManager.getInstance().getHomeBeanData().toString());
       if(CacheManager.getInstance().getHomeBeanData()!=null){
           list=(((HomeBean) CacheManager.getInstance().getHomeBeanData()).getResult() );
       }
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
            adapter=new HomeAdapter(((HomeBean)data).getResult(),getContext());
// 设置网格布局
            rv.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            rv.setAdapter(adapter);
        }else{
        }
    }
}
