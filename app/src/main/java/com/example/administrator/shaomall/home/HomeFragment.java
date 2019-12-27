package com.example.administrator.shaomall.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.shaomall.R;
import com.example.administrator.shaomall.cache.CacheManager;
import com.example.administrator.shaomall.message.MessageActivity;
import com.example.administrator.shaomall.search.SearchActivity;
import com.example.commen.custom.NetWorkHintCustom;
import com.example.commen.network.NetType;
import com.example.commen.network.NetWorkUtils;
import com.example.commen.view.AnimationNestedScrollView;
import com.example.administrator.shaomall.home.adapter.HomeRecycleAdapter;
import com.example.commen.util.CommonUtil;

import com.shaomall.framework.base.BaseFragment;
import com.shaomall.framework.bean.HomeBean;
import com.shaomall.framework.bean.MessageBean;
import com.shaomall.framework.manager.MessageManager;

import java.util.List;

import q.rorbin.badgeview.QBadgeView;

public class HomeFragment extends BaseFragment implements MessageManager.MessageListener, CacheManager.IHomeReceivedListener, View.OnClickListener {
    private AnimationNestedScrollView sv_view;
    private LinearLayout ll_search;
    private TextView tv_title;
    private float LL_SEARCH_MIN_TOP_MARGIN, LL_SEARCH_MAX_TOP_MARGIN, LL_SEARCH_MAX_WIDTH, LL_SEARCH_MIN_WIDTH, TV_TITLE_MAX_TOP_MARGIN;
    private ViewGroup.MarginLayoutParams searchLayoutParams, titleLayoutParams;
    private android.support.v7.widget.RecyclerView mHomeRecycler;
    private TextView message;
    private QBadgeView qBadgeView;
    private TextView searchTv;
    private NetWorkHintCustom mTvNetHint;
    private HomeRecycleAdapter homeRecycleAdapter;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        CacheManager.getInstance().registerListener(this);
        MessageManager.getInstance().registerMessageListener(this);

        mHomeRecycler = view.findViewById(R.id.home_recycler);
        sv_view = view.findViewById(R.id.search_sv_view);
        ll_search = view.findViewById(R.id.search_ll_search);
        tv_title = view.findViewById(R.id.search_tv_title);
        message = view.findViewById(R.id.search_tv_message);
        searchTv = view.findViewById(R.id.search_tv_search);
        mTvNetHint = view.findViewById(R.id.tv_net_hint);

        //配置Recycler
        mHomeRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        searchLayoutParams = (ViewGroup.MarginLayoutParams) ll_search.getLayoutParams();
        titleLayoutParams = (ViewGroup.MarginLayoutParams) tv_title.getLayoutParams();


        //点击事件
        message.setOnClickListener(this);   //消息界面
        searchTv.setOnClickListener(this);  //搜索界面

        qBadgeView = new QBadgeView(getContext());
        qBadgeView.bindTarget(message)
                .setBadgeTextSize(10f, true)
                .setBadgeGravity(Gravity.START | Gravity.TOP)
                .setBadgeBackgroundColor(Color.BLUE);
    }

    protected void initData() {
        setTitle();
        HomeBean.ResultBean data = CacheManager.getInstance().getHomeBean();

        //设置适配器
        homeRecycleAdapter = new HomeRecycleAdapter(data, getContext());
        mHomeRecycler.setAdapter(homeRecycleAdapter);

        List<MessageBean> messageBeans = MessageManager.getInstance().qurayNotReadData();
        int size = messageBeans.size();
        qBadgeView.setBadgeNumber(size);


        if (NetWorkUtils.isNetWorkAvailable()) {
            //请求网络数据
            CacheManager.getInstance().getData();
        } else {
            mTvNetHint.showView();
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.search_tv_message) { //消息界面
            toClass(MessageActivity.class);

        } else if (id == R.id.search_tv_search) {//搜索界面
            toClass(SearchActivity.class);

        }
    }

    /**
     * 网络已连接
     *
     * @param type
     */
    @Override
    public void onConnected(NetType type) {
        //        mTvNetHint.setVisibility(View.GONE);
        mTvNetHint.hideView();
        if (type == NetType.WIFI) { //wifi刷新数据
            CacheManager.getInstance().getData();//重新请求数据
        }
    }

    /**
     * 无网络
     */
    @Override
    public void onDisConnected() {
        //        mTvNetHint.setVisibility(View.VISIBLE);
        mTvNetHint.showView();
    }

    /**
     * 加载数据
     *
     * @param homeBean
     */
    @Override
    public void onHomeDataReceived(HomeBean.ResultBean homeBean) {
        if (homeRecycleAdapter != null)
            homeRecycleAdapter.upData(homeBean); //更新数据

    }


    private void setTitle() {
        LL_SEARCH_MIN_TOP_MARGIN = CommonUtil.dp2px(getContext(), 4.5f);//布局关闭时顶部距离
        LL_SEARCH_MAX_TOP_MARGIN = CommonUtil.dp2px(getContext(), 49f);//布局默认展开时顶部距离
        LL_SEARCH_MAX_WIDTH = CommonUtil.getScreenWidth(getContext()) - CommonUtil.dp2px(getContext(), 30f);//布局默认展开时的宽度
        // LL_SEARCH_MIN_WIDTH = CommonUtil.getScreenWidth(getContext()) - CommonUtil.dp2px(getContext(), 82f);//布局关闭时的宽度
        LL_SEARCH_MIN_WIDTH = CommonUtil.getScreenWidth(getContext()) - CommonUtil.dp2px(getContext(), 90f);//布局关闭时的宽度
        TV_TITLE_MAX_TOP_MARGIN = CommonUtil.dp2px(getContext(), 11.5f);

        sv_view.setOnAnimationScrollListener(new AnimationNestedScrollView.OnAnimationScrollChangeListener() {
            @Override
            public void onScrollChanged(float dy) {
                float searchLayoutNewTopMargin = LL_SEARCH_MAX_TOP_MARGIN - dy;
                // float searchLayoutNewWidth = LL_SEARCH_MAX_WIDTH - dy * 1.3f;//此处 * 1.3f 可以设置搜索框宽度缩放的速率
                float searchLayoutNewWidth = LL_SEARCH_MAX_WIDTH - dy * 3.0f;//此处 * 1.3f 可以设置搜索框宽度缩放的速率

                float titleNewTopMargin = (float) (TV_TITLE_MAX_TOP_MARGIN - dy * 0.5);

                //处理布局的边界问题
                searchLayoutNewWidth = searchLayoutNewWidth < LL_SEARCH_MIN_WIDTH ? LL_SEARCH_MIN_WIDTH : searchLayoutNewWidth;

                if (searchLayoutNewTopMargin < LL_SEARCH_MIN_TOP_MARGIN) {
                    searchLayoutNewTopMargin = LL_SEARCH_MIN_TOP_MARGIN;
                }

                if (searchLayoutNewWidth < LL_SEARCH_MIN_WIDTH) {
                    searchLayoutNewWidth = LL_SEARCH_MIN_WIDTH;
                }

                float titleAlpha = 255 * titleNewTopMargin / TV_TITLE_MAX_TOP_MARGIN;
                if (titleAlpha < 0) {
                    titleAlpha = 0;
                }

                //设置相关控件的LayoutParams  此处使用的是MarginLayoutParams，便于设置params的topMargin属性
                tv_title.setTextColor(tv_title.getTextColors().withAlpha((int) titleAlpha));
                titleLayoutParams.topMargin = (int) titleNewTopMargin;
                tv_title.setLayoutParams(titleLayoutParams);

                searchLayoutParams.topMargin = (int) searchLayoutNewTopMargin;
                searchLayoutParams.width = (int) searchLayoutNewWidth;
                ll_search.setLayoutParams(searchLayoutParams);
            }
        });
    }


    @Override
    public void getMessage(MessageBean messageBean, int messageNum) {
        qBadgeView.setBadgeNumber(messageNum);
    }

    @Override
    public void onResume() {
        super.onResume();
        int i = MessageManager.getInstance().gitNotReadNum();
        qBadgeView.setBadgeNumber(i);
    }

    @Override
    public void onDestroy() {
        MessageManager.getInstance().unRegisterMessageListener(this);
        super.onDestroy();
    }

}
