package com.example.dimensionleague.find;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buy.activity.GoodsActiviy;
import com.example.buy.databeans.GoodsBean;
import com.example.common.HomeBean;
import com.example.common.code.Constant;

import com.example.common.utils.IntentUtil;
import com.example.dimensionleague.R;
import com.example.dimensionleague.home.HomePresenter;
import com.example.framework.base.BaseNetConnectFragment;
import com.example.framework.base.BaseRecyclerAdapter;
import com.example.framework.base.BaseViewHolder;
import com.example.framework.port.IPresenter;
import com.example.net.AppNetConfig;

import java.util.ArrayList;
import java.util.List;


public class FindSendFragment extends BaseNetConnectFragment {

    private List<HomeBean.ResultBean.SeckillInfoBean.ListBean> list;
    private RecyclerView rv;
    private IPresenter homePresenter;

    @Override
    public int getLayoutId() {
        return R.layout.find_send_rv;
    }


    @Override
    public void init(View view) {
        super.init(view);
        rv = view.findViewById(R.id.find_rv);
        list = new ArrayList<>();
        homePresenter = new HomePresenter();
    }

    @Override
    public void initDate() {
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv.setAdapter(new BaseRecyclerAdapter<HomeBean.ResultBean.SeckillInfoBean.ListBean>(R.layout.item_mine_rv_recommend, list) {
            @Override
            public void onBind(BaseViewHolder holder, int position) {
                holder.getTextView(R.id.mine_rcv_recommend_title, list.get(position).getName());
                holder.getTextView(R.id.mine_rcv_recommend_price, list.get(position).getCover_price());
                holder.getImageView(R.id.mine_rcv_recommend_img, AppNetConfig.BASE_URl_IMAGE + list.get(position).getFigure())
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), GoodsActiviy.class);
                                intent.putExtra(IntentUtil.GOTO_GOOD, new GoodsBean(
                                        list.get(position).getProduct_id(),
                                        0,
                                        list.get(position).getName(),
                                        list.get(position).getFigure(),
                                        list.get(position).getCover_price()));
                                getContext().startActivity(intent);
                            }
                        });
            }
        });
        homePresenter.attachView(this);
        homePresenter.doHttpGetRequest();
    }


    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public int getRelativeLayout() {
        return R.id.findRelativeLayout;
    }


    @Override
    public void onRequestSuccess(Object data) {
        if (rv!=null){
            if (data != null) {
                int code = ((HomeBean) data).getCode();
                String msg = ((HomeBean) data).getMsg();
                if (String.valueOf(code).equals(Constant.CODE_OK)) {
                    list.clear();
                    list.addAll(((HomeBean) data).getResult().getRecommend_info());
                    if (rv!=null){
                        rv.getAdapter().notifyDataSetChanged();
                    }
                } else {
                    toast(getActivity(), msg);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        homePresenter.detachView();
        rv=null;
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        rv=null;
        homePresenter.detachView();
        super.onDestroyView();
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
