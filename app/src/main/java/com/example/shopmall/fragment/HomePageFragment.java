package com.example.shopmall.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.LoadingPage;
import com.example.common.TitleBar;
import com.example.framework.base.IGetBaseView;
import com.example.framework.base.ILoadView;
import com.example.framework.bean.MessageBean;
import com.example.framework.manager.CaCheManager;
import com.example.framework.base.BaseFragment;
import com.example.framework.manager.ConnectManager;
import com.example.framework.manager.MessageManager;
import com.example.framework.manager.UserManager;
import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.shopmall.activity.LoginActivity;
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
    private IntegerPresenter integerPresenter;

    //统计未读取消息数量
    private int sum = 0;

    @Override
    public void onStart() {
        super.onStart();

        if (UserManager.getInstance().getLoginStatus()) {
            handler.sendEmptyMessage(100);
        }else {
            tbHomepage.setMessageShow(false);
        }

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == 100){
                List<MessageBean> messages = MessageManager.getInstance().getMessage();
                if (messages.size() > 0) {
                    for (int i = 0; i < messages.size(); i++) {
                        if (!messages.get(i).getIsMessage()) {
                            sum++;
                            if (sum > 0){
                                tbHomepage.setMessageShow(true);
                            }else {
                                tbHomepage.setMessageShow(false);
                            }
                        }else {
                            sum = 0;
                        }
                    }
                }
            }
        }
    };

    @Override
    protected void initData() {
        tbHomepage.setTitleBacKGround(Color.RED);
        tbHomepage.setCenterText("首页", 18, Color.WHITE);
        tbHomepage.setRightImg(R.mipmap.new_message_icon);

        tbHomepage.setTitleClickLisner(new TitleBar.TitleClickLisner() {
            @Override
            public void LeftClick() {

            }

            @Override
            public void RightClick() {
                //跳转到消息
                if (UserManager.getInstance().getLoginStatus()){ //判断登录，登录后跳转到消息
                    startActivity(new Intent(getContext(), MessageActivity.class));
                }else {//没有登录跳转到登录页登录
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    intent.putExtra("mainitem",0);
                    startActivity(intent);
                }
            }

            @Override
            public void CenterClick() {
            }
        });

        //检查是否有网络
        boolean connectStatus = ConnectManager.getInstance().getConnectStatus();

        //从缓存中获取数据
        if (connectStatus){
            HomepageBean cacheBean = new CaCheManager(getContext()).getCacheBean(getContext());
            List<HomepageBean.ResultBean> resultBeans = new ArrayList<>();
            if (cacheBean != null) {
                resultBeans.clear();
                resultBeans.add(cacheBean.getResult());
                HomePageAdapter home_page_adapter = new HomePageAdapter(getContext());
                home_page_adapter.reFresh(resultBeans);
                rvHomePage.setAdapter(home_page_adapter);
            }
        }

        if (connectStatus) {//有网络
            //从网上获取数据
            integerPresenter = new IntegerPresenter(Constant.HOME_URL, HomepageBean.class);
            integerPresenter.attachGetView(this);
            integerPresenter.attachLoadView(this);
            integerPresenter.getGetData();
        } else {
            Toast.makeText(getContext(), "无网络连接", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        integerPresenter = new IntegerPresenter(Constant.HOME_URL, HomepageBean.class);
        integerPresenter.attachGetView(this);
        integerPresenter.getGetData();
    }

    @Override
    protected void initView(View view) {
        tbHomepage = view.findViewById(R.id.tb_homepage);
        rvHomePage = view.findViewById(R.id.rv_home_page);
        lpLoadingPageHomePage = view.findViewById(R.id.lp_loadingPage_home_page);

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
        }, 1000);
    }

    //开始显示loadingPage
    @Override
    public void onLoadingPage() {
        lpLoadingPageHomePage.start(LoadingPage.LOADING_SUCCEED);
        lpLoadingPageHomePage.setVisibility(View.VISIBLE);
        rvHomePage.setVisibility(View.GONE);
    }

    //结束显示loadingPage
    @Override
    public void onStopLoadingPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lpLoadingPageHomePage.isSucceed();
                rvHomePage.setVisibility(View.VISIBLE);
            }
        }, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (integerPresenter != null){
            integerPresenter.detachView();
        }
        lpLoadingPageHomePage.DetachLoadingView();
        handler.removeCallbacksAndMessages(this);
    }
}
