package com.example.shopmall.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.buy.bean.InsertBean;
import com.example.buy.presenter.InsertPresenter;
import com.example.common.BottomBar;
import com.example.common.SignUtil;
import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.base.IPostBaseView;
import com.example.framework.manager.ShoppingManager;
import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.shopmall.adapter.GoodsInfoAdapter;
import com.example.shopmall.bean.GoodsBean;


import java.util.ArrayList;
import java.util.HashMap;

public class GoodsInfoActivity extends BaseActivity implements IPostBaseView {

    TitleBar tb_goods_info;
    RecyclerView rv_goods_info;
    BottomBar bb_goods_info;
    Button bt_goods_info;

    ArrayList<GoodsBean> list_goods = new ArrayList<>();

    private InsertPresenter addOneProduct;

    @Override
    protected int setLayout() {
        return R.layout.activity_goods_info;
    }

    @Override
    public void initView() {
        tb_goods_info = findViewById(R.id.tb_goods_info);
        rv_goods_info = findViewById(R.id.rv_goods_info);
        bb_goods_info = findViewById(R.id.bb_goods_info);
        bt_goods_info = findViewById(R.id.bt_goods_info);

        rv_goods_info.setLayoutManager(new LinearLayoutManager(this));
        list_goods.clear();
    }

    @Override
    public void initData() {
        tb_goods_info.setTitleBacKGround(Color.RED);
        tb_goods_info.setCenterText("商品详情",18,Color.WHITE);
        tb_goods_info.setLeftImg(R.drawable.left);

        tb_goods_info.setTitleClickLisner(new TitleBar.TitleClickLisner() {
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

        String[] strs = new String[]{"联系客服","收藏","购物车"};
        final Drawable mine = getResources().getDrawable(R.drawable.mine);
        final Drawable collect = getResources().getDrawable(R.drawable.collect);
        final Drawable shoppingcart = getResources().getDrawable(R.drawable.shoppingcart);
        Drawable[] drawables = new Drawable[]{mine,collect,shoppingcart};
        bb_goods_info.setBottombarName(strs);
        bb_goods_info.setTapDrables(drawables);

        //底部导航
        bb_goods_info.setOnTapListener(new BottomBar.OnTapListener() {
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


        Intent intent = getIntent();
        final GoodsBean goods_bean = (GoodsBean) intent.getSerializableExtra("goods_bean");
        list_goods.add(goods_bean);

        GoodsInfoAdapter goodsInfoAdapter = new GoodsInfoAdapter(this, list_goods);
        rv_goods_info.setAdapter(goodsInfoAdapter);

        //加入购物车
        bt_goods_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = ShoppingManager.getInstance().getToken(GoodsInfoActivity.this);
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
                addOneProduct.getPostJsonData();
                addOneProduct.attachPostView(GoodsInfoActivity.this);

            }
        });
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
    public void onPostDataSucess(Object data) {

    }

    @Override
    public void onPostDataFailed(String ErrorMsg) {
        Log.d("####", ErrorMsg);
    }
}
