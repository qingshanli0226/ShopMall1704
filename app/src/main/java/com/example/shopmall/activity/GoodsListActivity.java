package com.example.shopmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.base.IBaseView;
import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.shopmall.bean.ClassifyBean;
import com.example.shopmall.presenter.IntegerPresenter;

public class GoodsListActivity extends BaseActivity implements IBaseView<GoodsListActivity> {

    TitleBar tb_goods_list;
    RecyclerView rv_goods_list;

    IntegerPresenter integerPresenter;

    private String[] urls = new String[]{Constant.CLOSE_STORE, Constant.GAME_STORE, Constant.COMIC_STORE, Constant.COSPLAY_STORE,
            Constant.GUFENG_STORE, Constant.STICK_STORE, Constant.WENJU_STORE, Constant.FOOD_STORE, Constant.SHOUSHI_STORE,};

    @Override
    protected int setLayout() {
        return R.layout.activity_goods_list;
    }

    @Override
    public void initView() {
        tb_goods_list = findViewById(R.id.tb_goods_list);
        rv_goods_list = findViewById(R.id.rv_goods_list);

        rv_goods_list.setLayoutManager(new GridLayoutManager(this,2));

    }

    @Override
    public void initData() {
        tb_goods_list.setTitleBacKGround(Color.RED);
        tb_goods_list.setLeftImg(R.drawable.left);
        tb_goods_list.setCenterText("商品内容",18,Color.WHITE);

        tb_goods_list.setTitleClickLisner(new TitleBar.TitleClickLisner() {
            @Override
            public void LeftClick() {

            }

            @Override
            public void RightClick() {

            }

            @Override
            public void CenterClick() {

            }
        });
    }

    private void getDataPresenter(String url) {
        integerPresenter = new IntegerPresenter(url, ClassifyBean.class);
        integerPresenter.attachView(this);
        integerPresenter.getGetData();
    }

    @Override
    public void onGetDataSucess(GoodsListActivity data) {

    }

    @Override
    public void onPostDataSucess(GoodsListActivity data) {

    }

    @Override
    public void onGetDataFailed(String ErrorMsg) {

    }

    @Override
    public void onLoadingPage() {

    }

    @Override
    public void onStopLoadingPage() {

    }
}
