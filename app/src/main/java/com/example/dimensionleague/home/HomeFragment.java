package com.example.dimensionleague.home;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.HomeBean;
import com.example.common.code.Constant;
import com.example.common.view.MyToolBar;
import com.example.dimensionleague.CacheManager;
import com.example.dimensionleague.R;
import com.example.dimensionleague.home.adapter.HomeAdapter;
import com.example.framework.base.BaseNetConnectFragment;
import com.example.framework.base.BasePresenter;
import com.example.framework.port.AppBarStateChangeListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class HomeFragment extends BaseNetConnectFragment {
    private RecyclerView rv;
    private HomeAdapter adapter;
    private HomeBean.ResultBean list = new HomeBean.ResultBean();
    private BasePresenter iBasePresenter;

    private int type = 0;
    private MyToolBar my_toolbar;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout toolbar_layout;

    public HomeFragment(int i) {
        super();
        this.type = i;

    }

    //    无参构造
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

        iBasePresenter = new HomePresenter();
//        判断type值来显示隐藏搜索框
        if (type == 1) {
            appBarLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void initDate() {
//        iBasePresenter.attachView(this);
//        iBasePresenter.doHttpGetRequest();


//        Log.e("SSS",CacheManager.getInstance().getHomeBeanData().toString());

        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Log.d("lhfff", "onStateChanged: " + state.name());
                if (state == State.EXPANDED) { //TODO 展开状态
                    my_toolbar.GoneAll();
                } else if (state == State.COLLAPSED) {//TODO 折叠状态
                    my_toolbar.init(Constant.HOME_STYLE);
                } else {  //TODO 中间状态

                }
            }
        });

        toolbar_layout.setContentScrim(getResources().getDrawable(R.drawable.home_toolbar));
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
                Toast.makeText(getActivity(), "跳转到搜索页面", Toast.LENGTH_SHORT).show();
            }
        });
        if (CacheManager.getInstance().getHomeBeanData() != null) {
            list = (((HomeBean) CacheManager.getInstance().getHomeBeanData()).getResult());
        }
        adapter = new HomeAdapter(list, getContext());
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
        if (data != null) {
            adapter = new HomeAdapter(((HomeBean) data).getResult(), getContext());
// 设置网格布局
            rv.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            rv.setAdapter(adapter);
        } else {
        }
    }
}
