package com.example.dimensionleague.mine;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.buy.activity.OrderActivity;
import com.example.common.HomeBean;
import com.example.common.utils.IntentUtil;
import com.example.common.code.Constant;
import com.example.common.view.MyToolBar;
import com.example.dimensionleague.setting.SettingActivity;
import com.example.framework.manager.AccountManager;
import com.example.common.port.IAccountCallBack;
import com.example.dimensionleague.R;
import com.example.dimensionleague.home.HomePresenter;
import com.example.dimensionleague.login.activity.LoginActivity;
import com.example.framework.base.BaseNetConnectFragment;
import com.example.framework.port.OnClickItemListener;
import com.example.net.AppNetConfig;
import com.example.point.activity.IntegralActivity;
import com.example.point.activity.StepActivity;

import java.util.ArrayList;
import java.util.List;

public class MineFragment extends BaseNetConnectFragment implements IAccountCallBack {

    private RecyclerView rvList, rvChannel, rvRecommend;
    private ImageView img;
    private TextView name;
    private RelativeLayout relative;
    private MineRecycleViewAdapter listAdapter;
    private MineRecycleAdapter channelAdapter;
    private MineRecommendAdapter recommendAdapter;
    private NestedScrollView nestedScrollView;
    private MyToolBar myToolBar;

    private HomePresenter homePresenter;
    private List<MineBean> list;
    private List<HomeBean.ResultBean.ChannelInfoBean> channelList;
    private List<HomeBean.ResultBean.SeckillInfoBean.ListBean> recommendlList;

    //TODO 缓存用户信息管理类
    private AccountManager accountManager = AccountManager.getInstance();

    @Override
    public void init(View view) {
        super.init(view);
        myToolBar = view.findViewById(R.id.mine_toolbar);
        nestedScrollView = view.findViewById(R.id.scroll_mine);
        rvList = view.findViewById(R.id.mine_rv_h);
        rvChannel = view.findViewById(R.id.mine_rv_v);
        rvRecommend = view.findViewById(R.id.mine_rv_recommend);
        name = view.findViewById(R.id.mine_user_name);
        img = view.findViewById(R.id.mine_img);
        relative = view.findViewById(R.id.mine_Relative);
        list = new ArrayList<>();
        channelList = new ArrayList<>();
        recommendlList = new ArrayList<>();
        homePresenter = new HomePresenter();
        rvList.setNestedScrollingEnabled(false);
        rvChannel.setNestedScrollingEnabled(false);
        rvRecommend.setNestedScrollingEnabled(false);

        myToolBar.init(Constant.MINE_STYLE);
        myToolBar.setBackground(getResources().getDrawable(R.drawable.toolbar_style));


        //TODO 给 更多 页面进行注册监听
        accountManager.registerUserCallBack(this);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initDate() {
        ifUser();
        list.add(new MineBean(R.drawable.mine_pay, getString(R.string.mine_pay)));
        list.add(new MineBean(R.drawable.mine_receiver, getString(R.string.mine_receiver)));
        list.add(new MineBean(R.drawable.mine_evaluate, getString(R.string.mine_evaluate)));
        list.add(new MineBean(R.drawable.mine_refund, getString(R.string.mine_refund)));
        list.add(new MineBean(R.drawable.mine_indent, getString(R.string.mine_indent)));
        list.add(new MineBean(R.drawable.mine_bean, getString(R.string.mine_bean)));
        list.add(new MineBean(R.drawable.mine_white, getString(R.string.mine_white)));
        list.add(new MineBean(R.drawable.mine_discounts, getString(R.string.mine_discounts)));
        list.add(new MineBean(R.drawable.mine_run, getString(R.string.mine_run)));
        list.add(new MineBean(R.drawable.mine_wallet, getString(R.string.mine_wallet)));
        homePresenter.attachView(this);
        homePresenter.doHttpGetRequest();
        listAdapter = new MineRecycleViewAdapter(R.layout.item_mine_rv, list);
        channelAdapter = new MineRecycleAdapter(R.layout.item_mine_rv_h, channelList);
        recommendAdapter = new MineRecommendAdapter(R.layout.item_mine_rv_recommend, recommendlList);
        mineListeners();
        nestedScrollView.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY < 530) {
                myToolBar.setAlpha(scrollY / 530.0f);
            } else {
                myToolBar.setAlpha(1.0f);
            }
        });
    }

    private void mineListeners() {
//        所有监听事件
        relative.setOnClickListener(v -> {
            if ((getString(R.string.mine_login).equals(name.getText().toString()))) {
//                登录注册跳转
                startActivity(LoginActivity.class, null);
            } else {
//                跳转到个人信息
                startActivity(SettingActivity.class, null);
            }
        });
        listAdapter.setClickListener(position -> {
            Bundle bundle = new Bundle();
            if (AccountManager.getInstance().isLogin()) {
                switch (position) {
                    case 0:
                        //待付款
                        bundle.putString(IntentUtil.ORDER_SHOW, Constant.WAIT_PAY);
                        startActivity(OrderActivity.class, bundle);
                        break;
                    case 1:
                        //待发货
                        bundle.putString(IntentUtil.ORDER_SHOW, Constant.WAIT_SEND);
                        startActivity(OrderActivity.class, bundle);
                        break;
                    case 2:
                        //待评价
                    case 3:
                        //退换/售后
                        toast(getActivity(), list.get(position).getTitle());
                        break;
                    case 4:
                        //我的订单
                        bundle.putString(IntentUtil.ORDER_SHOW, Constant.ALL_ORDER);
                        startActivity(OrderActivity.class, bundle);
                        break;
                    case 5:
                        //我的积分
                        startActivity(IntegralActivity.class, null);
                        break;
                    case 6:
                        //白条
                    case 7:
                        //优惠券
                        toast(getActivity(), list.get(position).getTitle());
                        break;
                    case 8:
                        //运动
                        startActivity(StepActivity.class, null);
                        break;
                    case 9:
                        //我的钱包
                        break;
                }
            } else {
                switch (position) {
                    case 0:
                        //待付款
                        bundle.putString(IntentUtil.LOGIN, Constant.WAIT_PAY);
                        startActivity(LoginActivity.class, bundle);
                        break;
                    case 1:
                        //待发货
                        bundle.putString(IntentUtil.LOGIN, Constant.WAIT_SEND);
                        startActivity(LoginActivity.class, bundle);
                        break;
                    case 2:
                        //待评价
                    case 3:
                        //退换/售后
                        startActivity(LoginActivity.class, null);
                        break;
                    case 4:
                        //我的订单
                        bundle.putString(IntentUtil.LOGIN, Constant.ALL_ORDER);
                        startActivity(LoginActivity.class, bundle);
                        break;
                    case 5:
                        //我的积分
                        bundle.putString(IntentUtil.LOGIN, Constant.MINE_INTEGRAL);
                        startActivity(LoginActivity.class, bundle);
                        break;
                    case 6:
                        //白条
                    case 7:
                        //优惠券
                        startActivity(LoginActivity.class, null);
                        break;
                    case 8:
                        //运动
                        bundle.putString(IntentUtil.LOGIN, Constant.EXERCISE);
                        startActivity(LoginActivity.class, bundle);
                        break;
                    case 9:
                        //我的钱包
                        startActivity(LoginActivity.class, null);
                        break;
                }

            }
        });
    }


    private void ifUser() {
        if (AccountManager.getInstance().isLogin()) {
            if (AccountManager.getInstance().getUser().getName() != null) {
                //登录
                name.setText(AccountManager.getInstance().getUser().getName());
                if (AccountManager.getInstance().getUser().getAvatar() != null) {
                    Glide.with(getContext()).load("" + AppNetConfig.BASE_URL + AccountManager.getInstance().getUser().getAvatar()).apply(new RequestOptions().circleCrop()).into(img);
                }
            }
        } else {
            //没有登录
            name.setText(R.string.mine_login);
            img.setImageResource(R.mipmap.ic_launcher_round);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public int getRelativeLayout() {
        return 0;
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);

        if (data != null) {
            int code = ((HomeBean) data).getCode();
            String msg = ((HomeBean) data).getMsg();
            if (code == Integer.parseInt(Constant.CODE_OK)) {
                channelList.addAll(((HomeBean) data).getResult().getChannel_info());
                recommendlList.addAll(((HomeBean) data).getResult().getRecommend_info());
                rvList.setLayoutManager(new GridLayoutManager(getContext(), 5));
                listAdapter.notifyDataSetChanged();
                rvChannel.setLayoutManager(new GridLayoutManager(getContext(), 5));
                channelAdapter.notifyDataSetChanged();
                rvRecommend.setLayoutManager(new GridLayoutManager(getContext(), 2));
                recommendAdapter.notifyDataSetChanged();
                rvList.setAdapter(listAdapter);
                rvChannel.setAdapter(channelAdapter);
                rvRecommend.setAdapter(recommendAdapter);
            } else {
                toast(getActivity(), msg);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (homePresenter != null) {
            homePresenter.detachView();
        }
        homePresenter = null;
        if (accountManager != null) {
            accountManager.unRegisterUserCallBack(this);
        }
        accountManager = null;
    }

    //TODO 用户注册成功后回调
    @Override
    public void onRegisterSuccess() {

    }

    //TODO 用户登录成功后回调
    @Override
    public void onLogin() {
        name.setText(AccountManager.getInstance().getUser().getName());
    }

    @Override
    public void onLogout() {
        name.setText(R.string.mine_login);
        img.setImageResource(R.mipmap.ic_launcher_round);
    }

    //TODO 用户更新头像后回调
    @Override
    public void onAvatarUpdate(String url) {
        Glide.with(getContext()).load("" + AppNetConfig.BASE_URL + url).apply(new RequestOptions().circleCrop()).into(img);
    }

    @Override
    public void onConnected() {
        homePresenter.doHttpGetRequest();
        myToolBar.isConnection(true);
    }

    @Override
    public void onDisConnected() {
        myToolBar.isConnection(false);
    }
}
