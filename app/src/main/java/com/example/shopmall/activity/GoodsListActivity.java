package com.example.shopmall.activity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.base.IGetBaseView;
import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.shopmall.adapter.GoodsListAdapter;
import com.example.shopmall.bean.GoodsListBean;
import com.example.shopmall.presenter.IntegerPresenter;

/**
 * 商品内容
 */
public class GoodsListActivity extends BaseActivity implements IGetBaseView<GoodsListBean> {

    private TitleBar tbGoodsList;
    private RecyclerView rvGoodsList;

    private String[] urls = new String[]{Constant.CLOSE_STORE, Constant.GAME_STORE, Constant.COMIC_STORE, Constant.COSPLAY_STORE,
            Constant.GUFENG_STORE, Constant.STICK_STORE, Constant.WENJU_STORE, Constant.FOOD_STORE, Constant.SHOUSHI_STORE,};

    @Override
    protected int setLayout() {
        return R.layout.activity_goods_list;
    }

    @Override
    public void initView() {
        tbGoodsList = findViewById(R.id.tb_goods_list);
        rvGoodsList = findViewById(R.id.rv_goods_list);

        rvGoodsList.setLayoutManager(new GridLayoutManager(this,2));

    }

    @Override
    public void initData() {

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        getDataPresenter(urls[position]);

        tbGoodsList.setTitleBacKGround(Color.RED);
        tbGoodsList.setLeftImg(R.drawable.left);
        tbGoodsList.setCenterText("商品内容",18,Color.WHITE);

        tbGoodsList.setTitleClickLisner(new TitleBar.TitleClickLisner() {
            @Override
            public void LeftClick() {
                finish();
            }

            @Override
            public void RightClick() {

            }

            @Override
            public void CenterClick() {

            }
        });
    }

    //网络获取商品内容数据
    private void getDataPresenter(String url) {
        IntegerPresenter integerPresenter = new IntegerPresenter(url, GoodsListBean.class);
        integerPresenter.attachGetView(this);
        integerPresenter.getGetData();
    }

    //显示从网络获取到的数据
    @Override
    public void onGetDataSucess(GoodsListBean data) {
        GoodsListAdapter goodsListAdapter = new GoodsListAdapter(this);
        goodsListAdapter.reFresh(data.getResult().getPage_data());
        rvGoodsList.setAdapter(goodsListAdapter);
    }

    @Override
    public void onGetDataFailed(String ErrorMsg) {

    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
