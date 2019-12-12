package com.example.shopmall.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.LoadingPage;
import com.example.common.TitleBar;
import com.example.framework.base.IGetBaseView;
import com.example.framework.base.ILoadView;
import com.example.framework.manager.CaCheManager;
import com.example.framework.base.BaseFragment;
import com.example.framework.manager.ConnectManager;
import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.shopmall.activity.MessageActivity;
import com.example.shopmall.adapter.HomePageAdapter;
import com.example.framework.bean.HomepageBean;
import com.example.shopmall.presenter.IntegerPresenter;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends BaseFragment implements IGetBaseView<HomepageBean>, ILoadView {

    private TitleBar tbHomepage;
    private RecyclerView rvHomePage;
    private LoadingPage lpLoadingPageHomePage;
    private LinearLayout llHomePage;

    @Override
    protected void initData() {
        tbHomepage.setTitleBacKGround(Color.RED);
        tbHomepage.setCenterText("首页",18,Color.WHITE);
        tbHomepage.setRightImg(R.mipmap.new_message_icon);

        tbHomepage.setTitleClickLisner(new TitleBar.TitleClickLisner() {
            @Override
            public void LeftClick() {

            }

            @Override
            public void RightClick() {
                //消息
                startActivity(new Intent(getContext(), MessageActivity.class));
            }

            @Override
            public void CenterClick() {

            }
        });

        //从缓存中获取数据
        HomepageBean cacheBean = new CaCheManager(getContext()).getCacheBean(getContext());
        List<HomepageBean.ResultBean> resultBeans = new ArrayList<>();
        resultBeans.clear();
        resultBeans.add(cacheBean.getResult());
        HomePageAdapter home_page_adapter = new HomePageAdapter(getContext());
        home_page_adapter.reFresh(resultBeans);
        rvHomePage.setAdapter(home_page_adapter);

        //检查是否有网络
        boolean connectStatus = ConnectManager.getInstance().getConnectStatus();
        if (connectStatus) {//有网络
            //从网上获取数据
            IntegerPresenter integerPresenter = new IntegerPresenter(Constant.HOME_URL, HomepageBean.class);
            integerPresenter.attachGetView(this);
            integerPresenter.attachLoadView(this);
            integerPresenter.getGetData();

        } else {
            Toast.makeText(getContext(), "无网络连接", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void initView(View view) {
        tbHomepage = view.findViewById(R.id.tb_homepage);
        rvHomePage = view.findViewById(R.id.rv_home_page);
        lpLoadingPageHomePage = view.findViewById(R.id.lp_loadingPage_home_page);
        llHomePage = view.findViewById(R.id.ll_home_page);

        rvHomePage.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_home_page;
    }

    @Override
    public void onGetDataSucess(HomepageBean data) {
        List<HomepageBean.ResultBean> resultBeans = new ArrayList<>();
        resultBeans.clear();
        resultBeans.add(data.getResult());
        HomePageAdapter home_page_adapter = new HomePageAdapter(getContext());
        home_page_adapter.reFresh(resultBeans);
        rvHomePage.setAdapter(home_page_adapter);
    }

    //网络获取失败显示loadingPage
    @Override
    public void onGetDataFailed(String ErrorMsg) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lpLoadingPageHomePage.start(LoadingPage.LOADING_FAILURE);
            }
        },1000);
    }

    //开始显示loadingPage
    @Override
    public void onLoadingPage() {
        lpLoadingPageHomePage.start(LoadingPage.LOADING_SUCCEED);
        lpLoadingPageHomePage.setVisibility(View.VISIBLE);
        llHomePage.setVisibility(View.GONE);
    }

    //结束显示loadingPage
    @Override
    public void onStopLoadingPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lpLoadingPageHomePage.isSucceed();
                llHomePage.setVisibility(View.VISIBLE);
            }
        },1000);
    }
}
