package com.example.dimensionleague.find;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.databinding.ViewDataBinding;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.buy.activity.GoodsActiviy;
import com.example.common.HomeBean;
import com.example.common.code.Constant;

import com.example.common.utils.IntentUtil;
import com.example.dimensionleague.R;
import com.example.dimensionleague.home.HomePresenter;
import com.example.dimensionleague.home.adapter.RecommendAdapter;
import com.example.dimensionleague.mine.MineRecommendAdapter;
import com.example.framework.base.BaseBindFragment;
import com.example.framework.base.BaseNetConnectFragment;
import com.example.framework.port.OnClickItemListener;

import java.util.ArrayList;
import java.util.List;



public class FindSendFragment extends BaseNetConnectFragment {

    private List<HomeBean.ResultBean.SeckillInfoBean.ListBean> list;
    private RecyclerView rv;
    private HomePresenter homePresenter;
    private MineRecommendAdapter recommendAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.find_send_rv;
    }


    @Override
    public void init(View view) {
        super.init(view);
        rv=view.findViewById(R.id.find_rv);
        list=new ArrayList<>();
        homePresenter = new HomePresenter();
    }

    @Override
    public void initDate() {
        homePresenter.attachView(this);
        homePresenter.doHttpGetRequest();
        recommendAdapter = new MineRecommendAdapter(R.layout.item_mine_rv_recommend, list);

        recommendAdapter.setClickListener(position -> {
            Intent intent =new Intent(getContext(), GoodsActiviy.class);
            intent.putExtra(IntentUtil.GOTO_GOOD,list.get(position));
            startActivity(intent);
        });
    }


    @Override
    public void showLoading() {}

    @Override
    public void hideLoading() {}

    @Override
    public int getRelativeLayout() {
        return R.id.find_relativeLayout;
    }


    @Override
    public void onRequestSuccess(Object data) {
        if (data != null) {
            int code = ((HomeBean) data).getCode();
            String msg = ((HomeBean) data).getMsg();
            if (String.valueOf(code).equals(Constant.CODE_OK)) {
                list.addAll(((HomeBean) data).getResult().getRecommend_info());
                rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
                recommendAdapter.notifyDataSetChanged();
                rv.setAdapter(recommendAdapter);
            } else {
                toast(getActivity(), msg);
            }
        }
    }
    @Override
    public void onConnected() {
        hideEmpty();
    }

    @Override
    public void onDisConnected() {
        hideLoading();
        hideError();
        showEmpty();
    }
}
