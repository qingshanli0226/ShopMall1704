package com.example.shopmall.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.buy.bean.InsertBean;
import com.example.buy.presenter.InsertPresenter;
import com.example.common.BottomBar;
import com.example.common.LoadingPage;
import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.base.IGetBaseView;
import com.example.framework.manager.ShoppingManager;
import com.example.shopmall.R;
import com.example.shopmall.adapter.GoodsInfoAdapter;
import com.example.shopmall.bean.GoodsBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 商品详情
 */
public class GoodsInfoActivity extends BaseActivity implements IGetBaseView {

    private TitleBar tbGoodsInfo;
    private RecyclerView rvGoods_info;
    private BottomBar bbGoodsInfo;
    private Button btGoodsInfo;

    private LoadingPage lpLoadingPageGoodsInfo;
    private LinearLayout llGoodsInfo;

    private ArrayList<GoodsBean> listGoods = new ArrayList<>();

    private InsertPresenter insertPresenter;

    @Override
    protected int setLayout() {
        return R.layout.activity_goods_info;
    }

    @Override
    public void initView() {
        tbGoodsInfo = findViewById(R.id.tb_goods_info);
        rvGoods_info = findViewById(R.id.rv_goods_info);
        bbGoodsInfo = findViewById(R.id.bb_goods_info);
        btGoodsInfo = findViewById(R.id.bt_goods_info);
        lpLoadingPageGoodsInfo = findViewById(R.id.lp_loadingPage_goods_info);
        llGoodsInfo = findViewById(R.id.ll_goods_info);

        rvGoods_info.setLayoutManager(new LinearLayoutManager(this));
        listGoods.clear();
    }

    @Override
    public void initData() {

        lpLoadingPageGoodsInfo.start(LoadingPage.LOADING_SUCCEED);
        lpLoadingPageGoodsInfo.setVisibility(View.VISIBLE);
        llGoodsInfo.setVisibility(View.GONE);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                lpLoadingPageGoodsInfo.isSucceed();
                llGoodsInfo.setVisibility(View.VISIBLE);
            }
        },1000);

        tbGoodsInfo.setTitleBacKGround(Color.RED);
        tbGoodsInfo.setCenterText("商品详情",18,Color.WHITE);
        tbGoodsInfo.setLeftImg(R.drawable.left);

        tbGoodsInfo.setTitleClickLisner(new TitleBar.TitleClickLisner() {
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

        String[] strs = new String[]{"联系客服","收藏","购物车"};
        final Drawable mine = getResources().getDrawable(R.drawable.mine);
        final Drawable collect = getResources().getDrawable(R.drawable.collect);
        final Drawable shoppingcart = getResources().getDrawable(R.drawable.shoppingcart);
        Drawable[] drawables = new Drawable[]{mine,collect,shoppingcart};
        bbGoodsInfo.setBottombarName(strs);
        bbGoodsInfo.setTapDrables(drawables);

        //底部导航
        bbGoodsInfo.setOnTapListener(new BottomBar.OnTapListener() {
            @Override
            public void tapItemClick(int i) {
                switch (i){
                    case 0:
                        mine();
                        break;
                    case 1:
                        collect();
                        break;
                    case 2:
                        shoppingcart();
                        break;
                }
            }
        });

        //加入购物车
        btGoodsInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = ShoppingManager.getInstance().getToken(GoodsInfoActivity.this);
                insertPresenter.attachGetView(GoodsInfoActivity.this);
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("token", token);
                insertPresenter = new InsertPresenter("addOneProduct", InsertBean.class, hashMap);
                insertPresenter.getGetData();

            }
        });

        Intent intent = getIntent();
        GoodsBean goods_bean = (GoodsBean) intent.getSerializableExtra("goods_bean");
        listGoods.add(goods_bean);

        GoodsInfoAdapter goods_info_adapter = new GoodsInfoAdapter();
        goods_info_adapter.reFresh(listGoods);
        rvGoods_info.setAdapter(goods_info_adapter);

    }

    //购物车
    private void shoppingcart() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("replacefragment", 3);
        startActivity(intent);
    }

    //收藏
    private void collect() {

    }

    //联系客服
    private void mine() {

    }

    @Override
    public void onGetDataSucess(Object data) {

    }

    @Override
    public void onGetDataFailed(String ErrorMsg) {

    }
}
