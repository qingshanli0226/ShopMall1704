package com.example.dimensionleague.mine;
import android.os.UserManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.common.manager.AccountManager;
import com.example.dimensionleague.R;
import com.example.dimensionleague.businessbean.HomeBean;
import com.example.dimensionleague.home.HomePresenter;
import com.example.framework.base.BaseNetConnectFragment;

import java.util.ArrayList;
import java.util.List;

public class MineFragment extends BaseNetConnectFragment {

    private RecyclerView rvList,rvChannel,rvRecommend;
    private MineRecycleViewAdapter listAdapter;
    private MineRecycleAdapter channelAdapter;
    private ImageView img;
    private HomePresenter homePresenter;
    private TextView name;
    private MineRecommendAdapter recommendAdapter;
    private List<MineBean> list;
    private List<HomeBean.ResultBean.ChannelInfoBean> channelList;
    private List<HomeBean.ResultBean.RecommendInfoBean> recommendlList;
    @Override
    public void init(View view) {
        super.init(view);
        list=new ArrayList<>();
        channelList=new ArrayList<>();
        recommendlList=new ArrayList<>();
        rvList=view.findViewById(R.id.mine_rv_h);
        rvChannel=view.findViewById(R.id.mine_rv_v);
        rvRecommend=view.findViewById(R.id.mine_rv_recommend);
        name=view.findViewById(R.id.mine_user_name);
        img =view.findViewById(R.id.mine_img);
        homePresenter=new HomePresenter();
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
        list.add(new MineBean(R.drawable.mine_pay,"待付款"));
        list.add(new MineBean(R.drawable.mine_receiver, "待收货"));
        list.add(new MineBean(R.drawable.mine_evaluate, "待评价"));
        list.add(new MineBean(R.drawable.mine_refund, "退换/售后"));
        list.add(new MineBean(R.drawable.mine_indent, "我的订单"));
        list.add(new MineBean(R.drawable.mine_bean, "我的积分"));
        list.add(new MineBean(R.drawable.mine_white, "白条"));
        list.add(new MineBean(R.drawable.mine_discounts, "优惠券"));
        list.add(new MineBean(R.drawable.mine_run, "运动"));
        list.add(new MineBean(R.drawable.mine_wallet, "我的钱包"));
        homePresenter.attachView(this);
        homePresenter.onHttpGetRequest();
    }

    private void ifUser() {
//        判断是否登录
        if(AccountManager.getInstance().isLogin()){
            //登录
            name.setText(AccountManager.getInstance().user.getName());
            if (AccountManager.getInstance().user.getAvatar()!=null){
                Glide.with(getContext()).load(AccountManager.getInstance().user.getAvatar()).into(img);
            }
        }else{
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

        if (data!=null){
            channelList.addAll(((HomeBean)data).result.channel_info);
            recommendlList.addAll(((HomeBean)data).result.recommend_info);
            rvList.setLayoutManager(new GridLayoutManager(getContext(),5));
            listAdapter=new MineRecycleViewAdapter(R.layout.item_mine_rv,list);
            rvChannel.setLayoutManager(new GridLayoutManager(getContext(),5));
            channelAdapter=new MineRecycleAdapter(R.layout.item_mine_rv_h,channelList);
            rvRecommend.setLayoutManager(new GridLayoutManager(getContext(),2));
            recommendAdapter=new MineRecommendAdapter(R.layout.item_mine_rv_recommend,recommendlList);
            rvList.setAdapter(listAdapter);
            rvChannel.setAdapter(channelAdapter);
            rvRecommend.setAdapter(recommendAdapter);
        }else {
            Log.i("SSSS", "onRequestSuccess: 没有");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (homePresenter!=null){
            homePresenter.detachView();
        }
        homePresenter=null;
    }
}
