package com.example.dimensionleague.mine;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.buy.activity.OrderActivity;
import com.example.common.HomeBean;

import com.example.common.IntentUtil;
import com.example.common.code.ErrorCode;
import com.example.framework.manager.AccountManager;
import com.example.common.port.IAccountCallBack;
import com.example.dimensionleague.R;
import com.example.dimensionleague.home.HomePresenter;
import com.example.dimensionleague.login.activity.LoginActivity;
import com.example.framework.base.BaseNetConnectFragment;
import com.example.framework.port.OnClickItemListener;
import com.example.point.activity.IntegralActivity;
import com.example.point.activity.StepActivity;

import java.util.ArrayList;
import java.util.List;

public class MineFragment extends BaseNetConnectFragment implements IAccountCallBack {

    private RecyclerView rvList, rvChannel, rvRecommend;
    private ImageView img;
    private TextView name;

    private MineRecycleViewAdapter listAdapter;
    private MineRecycleAdapter channelAdapter;
    private MineRecommendAdapter recommendAdapter;

    private HomePresenter homePresenter;

    private List<MineBean> list;
    private List<HomeBean.ResultBean.ChannelInfoBean> channelList;
    private List<HomeBean.ResultBean.SeckillInfoBean.ListBean> recommendlList;

    //TODO 缓存用户信息管理类
    private AccountManager accountManager = AccountManager.getInstance();

    @Override
    public void init(View view) {
        super.init(view);
        rvList = view.findViewById(R.id.mine_rv_h);
        rvChannel = view.findViewById(R.id.mine_rv_v);
        rvRecommend = view.findViewById(R.id.mine_rv_recommend);
        name = view.findViewById(R.id.mine_user_name);
        img = view.findViewById(R.id.mine_img);

        list = new ArrayList<>();
        channelList = new ArrayList<>();
        recommendlList = new ArrayList<>();
        homePresenter = new HomePresenter();

        //TODO 给 更多 页面进行注册监听
        accountManager.registerUserCallBack(this);

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void initDate() {
        ifUser();
        list.add(new MineBean(R.drawable.mine_pay, "待付款"));
        list.add(new MineBean(R.drawable.mine_receiver, "待发货"));
        list.add(new MineBean(R.drawable.mine_evaluate, "待评价"));
        list.add(new MineBean(R.drawable.mine_refund, "退换/售后"));
        list.add(new MineBean(R.drawable.mine_indent, "我的订单"));
        list.add(new MineBean(R.drawable.mine_bean, "我的积分"));
        list.add(new MineBean(R.drawable.mine_white, "白条"));
        list.add(new MineBean(R.drawable.mine_discounts, "优惠券"));
        list.add(new MineBean(R.drawable.mine_run, "运动"));
        list.add(new MineBean(R.drawable.mine_wallet, "我的钱包"));
        homePresenter.attachView(this);
        homePresenter.doHttpGetRequest();
        listAdapter = new MineRecycleViewAdapter(R.layout.item_mine_rv, list);
        channelAdapter = new MineRecycleAdapter(R.layout.item_mine_rv_h, channelList);
        recommendAdapter = new MineRecommendAdapter(R.layout.item_mine_rv_recommend, recommendlList);
        mineListeners();
    }

    private void mineListeners() {
//        所有监听事件
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                getContext().startActivity(intent);
            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoginActivity.class, null);

            }
        });

        listAdapter.setClickListener(new OnClickItemListener() {
            @Override
            public void onClickListener(int position) {
                Intent intent = new Intent();
                switch (position) {

                    case 0:
                        //待付款
                        intent.setClass(getContext(), OrderActivity.class);
                        intent.putExtra(IntentUtil.ORDER_SHOW, OrderActivity.WAIT_PAY);
                        getContext().startActivity(intent);
                        break;
                    case 1:
                        //待发货
                        intent.setClass(getContext(), OrderActivity.class);
                        intent.putExtra(IntentUtil.ORDER_SHOW, OrderActivity.WAIT_SEND);
                        getContext().startActivity(intent);
                        break;
                    case 2:
                        //待评价
                        toast(getActivity(), list.get(position).getTitle());
                        break;
                    case 3:
                        //退换/售后
                        toast(getActivity(), list.get(position).getTitle());
                        break;
                    case 4:
                        //我的订单
                        intent.setClass(getContext(), OrderActivity.class);
                        intent.putExtra(IntentUtil.ORDER_SHOW, OrderActivity.ALL);
                        getContext().startActivity(intent);
                        break;
                    case 5:
                         //我的积分
                        intent.setClass(getContext(), IntegralActivity.class);
                        getContext().startActivity(intent);
                        break;
                    case 6:
                        //白条
                        toast(getActivity(), list.get(position).getTitle());
                        break;
                    case 7:
                        //优惠券
                        toast(getActivity(), list.get(position).getTitle());
                        break;
                    case 8:
                        //运动
                        intent.setClass(getContext(), StepActivity.class);
                        getContext().startActivity(intent);
                        break;
                    case 9:
                        //我的钱包
                        break;
                }
            }
        });

    }

    private void ifUser() {
        if (AccountManager.getInstance().isLogin()) {
//            if(AccountManager.getInstance().user.getName() != null){
//                //登录
//                name.setText(AccountManager.getInstance().user.getName());
//                if (AccountManager.getInstance().user.getAvatar() != null) {
//                    Glide.with(getContext()).load(AccountManager.getInstance().user.getAvatar()).into(img);
//                }
//            }
        } else {
            //没有登录
            name.setText("登录/注册");
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
            if (code == 200) {
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
                switch (code) {
                    case 1001:
                        toast(getActivity(), ErrorCode.ERROR_USER_NOT_REGISTERED.getErrorMessage());
                        break;
                    case 1002:
                        toast(getActivity(), ErrorCode.ERROR_USER_HAS_REGISTERED.getErrorMessage());
                        break;
                    case 1003:
                        toast(getActivity(), ErrorCode.ERROR_NOT_LOGIN_REGISTERED.getErrorMessage());
                        break;
                    case 1004:
                        toast(getActivity(), ErrorCode.ERROR_UPLOAD_FILE_REGISTERED.getErrorMessage());
                        break;
                    case 1005:
                        toast(getActivity(), ErrorCode.ERROR_AUTO_LOGIN_REGISTERED.getErrorMessage());
                        break;
                    case 1006:
                        toast(getActivity(), ErrorCode.ERROR_TOKEN_EXPIRE_REGISTERED.getErrorMessage());
                        break;
                    case 1007:
                        toast(getActivity(), ErrorCode.ERROR_GET_ORDER_REGISTERED.getErrorMessage());
                        break;
                    case 1008:
                        toast(getActivity(), ErrorCode.ERROR_CHECK_INVENTORY_REGISTERED.getErrorMessage());
                        break;
                    case 1009:
                        toast(getActivity(), ErrorCode.ERROR_GET_SHORTCART_PRODUCTS_REGISTERED.getErrorMessage());
                        break;
                    case 1010:
                        toast(getActivity(), ErrorCode.ERROR_ADD_ONE_PRODUCT_REGISTERED.getErrorMessage());
                        break;
                    case 1011:
                        toast(getActivity(), ErrorCode.ERROR_UPDATE_PRODUCT_NUM_REGISTERED.getErrorMessage());
                        break;
                    case 1012:
                        toast(getActivity(), ErrorCode.ERROR_VERIFY_SIGN_REGISTERED.getErrorMessage());
                        break;
                }
            }
        } else {
            Log.i("SSSS", "onRequestSuccess: 没有");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (homePresenter != null) {
            homePresenter.detachView();
        }
        homePresenter = null;
    }

    //TODO 用户注册成功后回调
    @Override
    public void onRegisterSuccess() {

    }

    //TODO 用户登录成功后回调
    @Override
    public void onLogin() {
        Log.d("lhf", "onLogin: 这个方法进来了");
        name.setText(AccountManager.getInstance().user.getName());
        if (AccountManager.getInstance().user.getAvatar() != null) {
            Glide.with(getContext()).load(AccountManager.getInstance().user.getAvatar()).into(img);
        }
    }

    //TODO 用户退出登录后回调
    @Override
    public void onLogout() {
        name.setText("登录/注册");
        img.setImageResource(R.mipmap.ic_launcher_round);
    }

    //TODO 用户更新头像后回调
    @Override
    public void onAvatarUpdate(String url) {

    }
}
