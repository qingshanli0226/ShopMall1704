package com.example.shopmall.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.buy.bean.InsertBean;
import com.example.buy.presenter.InsertPresenter;
import com.example.common.BottomBar;
import com.example.common.SignUtil;
import com.example.common.TitleBar;
import com.example.common.view.MyOKButton;
import com.example.framework.base.BaseActivity;
import com.example.framework.base.IPostBaseView;
import com.example.framework.manager.ShoppingManager;
import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.shopmall.adapter.GoodsInfoAdapter;
import com.example.shopmall.bean.GoodsBean;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * 商品详情
 */
public class GoodsInfoActivity extends BaseActivity implements IPostBaseView<InsertBean> {

    private TitleBar tbGoodsInfo;
    private RecyclerView rvGoodsInfo;
    private BottomBar bbGoodsInfo;
    private MyOKButton btGoodsInfo;
    private LinearLayout layoutBottom;

    private ArrayList<GoodsBean> list_goods = new ArrayList<>();

    private int x = 1;

    private InsertPresenter addOneProduct;
    private GoodsBean goods_bean;
    private int mainitem;
    private GoodsInfoAdapter goodsInfoAdapter;
    private Drawable mine;
    private Drawable collect;
    private Drawable shoppingcart;

    @Override
    protected int setLayout() {
        return R.layout.activity_goods_info;
    }

    @Override
    public void initView() {
        tbGoodsInfo = findViewById(R.id.tb_goods_info);
        rvGoodsInfo = findViewById(R.id.rv_goods_info);
        bbGoodsInfo = findViewById(R.id.bb_goods_info);
        btGoodsInfo = findViewById(R.id.bt_goods_info);
        layoutBottom = findViewById(R.id.ll_bottombar);

        rvGoodsInfo.setLayoutManager(new LinearLayoutManager(this));
        list_goods.clear();
    }

    @Override
    public void initData() {

        initIntent();

        tbGoodsInfo.setTitleBacKGround(Color.WHITE);
        tbGoodsInfo.setCenterText("商品详情", 18, Color.BLACK);
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

        String[] strs = new String[]{"联系客服", "收藏", "购物车"};
        mine = getResources().getDrawable(R.drawable.mine);
        collect = getResources().getDrawable(R.drawable.collect);
        shoppingcart = getResources().getDrawable(R.drawable.shoppingcart);
        Drawable[] drawables = new Drawable[]{mine, collect, shoppingcart};
        bbGoodsInfo.setBottombarName(strs);
        bbGoodsInfo.setTapDrables(drawables);

        //底部导航
        bbGoodsInfo.setOnTapListener(new BottomBar.OnTapListener() {
            @Override
            public void tapItemClick(int i) {
                switch (i) {
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

        goodsInfoAdapter = new GoodsInfoAdapter(this);
        goodsInfoAdapter.reFresh(list_goods);
        rvGoodsInfo.setAdapter(goodsInfoAdapter);

        //加入购物车
        btGoodsInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("####", "点击");
                String token = ShoppingManager.getInstance().getToken(GoodsInfoActivity.this);
                ShoppingManager.getInstance().setOnNumberChanged(x);
                x++;
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("token", token);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("productId", goods_bean.getProduct_id());
                jsonObject.put("productNum", goods_bean.getNumber());
                jsonObject.put("productName", goods_bean.getName());
                jsonObject.put("url", Constant.BASE_URL_IMAGE + goods_bean.getFigure());
                jsonObject.put("productPrice", goods_bean.getCover_price());
                jsonObject.put("sign", SignUtil.generateJsonSign(jsonObject));

                SignUtil.encryptJsonParamsByBase64(jsonObject);

                addOneProduct = new InsertPresenter("addOneProduct", InsertBean.class, hashMap, jsonObject);
                addOneProduct.attachPostView(GoodsInfoActivity.this);
                addOneProduct.getPostJsonData();

                Toast.makeText(GoodsInfoActivity.this, "购物车内物品数量+1", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //跳转传过来的数据
    private void initIntent() {
        Intent intent = getIntent();
        goods_bean = (GoodsBean) intent.getSerializableExtra("goods_bean");
        list_goods.add(goods_bean);
    }

    //购物车
    private void shoppingcart() {
        ShoppingManager.getInstance().setMainitem(3);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    //收藏
    private void collect() {

    }

    //联系客服
    private void mine() {

    }

    @Override
    public void onPostDataSucess(InsertBean data) {

    }

    @Override
    public void onPostDataFailed(String ErrorMsg) {
        Log.d("####", ErrorMsg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        ShoppingManager.getInstance().setMainitem(mainitem);

        goodsInfoAdapter.getWebView();

        if (addOneProduct != null){
            addOneProduct.detachView();
        }

    }
}

