package com.example.shopmall.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.example.framework.manager.UserManager;
import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.shopmall.adapter.GoodsInfoAdapter;
import com.example.shopmall.bean.GoodsBean;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品详情
 */
public class GoodsInfoActivity extends BaseActivity implements IPostBaseView<InsertBean> {

    private TitleBar tbGoodsInfo;
    private RecyclerView rvGoodsInfo;
    private BottomBar bbGoodsInfo;
    private MyOKButton btGoodsInfo;
    RelativeLayout rlGoodsinfo;

    private ArrayList<GoodsBean> list_goods = new ArrayList<>();

    private int x = 1;
    private int flag = 0;

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
        rlGoodsinfo = findViewById(R.id.rl_app_goodsinfo);

        rvGoodsInfo.setLayoutManager(new LinearLayoutManager(this));
        list_goods.clear();
    }

    @Override
    public void initData() {

        initIntent();

        tbGoodsInfo.setTitleBacKGround(Color.WHITE);
        tbGoodsInfo.setCenterText("商品详情", 18, Color.BLACK);
        Intent intent = getIntent();
        goods_bean = (GoodsBean) intent.getSerializableExtra("goods_bean");

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
        final Drawable mine = getResources().getDrawable(R.drawable.mine);

        Drawable collect = null;
        List<Map<String, String>> collections = ShoppingManager.getInstance().getCollections(this);
        flag = 0;
        for (int i = 0; i < collections.size(); i++) {
            Map<String, String> map = collections.get(i);
            String id = map.get("id");
            if(id.equals(goods_bean.getProduct_id())){
                collect = getResources().getDrawable(R.drawable.collectred);
                flag = 1;
                break;
            }
        }

        if(flag==0){
            collect = getResources().getDrawable(R.drawable.collect);
        }
        Log.e("####",flag+"");
        final Drawable shoppingcart = getResources().getDrawable(R.drawable.shoppingcart);
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
        list_goods.add(goods_bean);

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


                addAnimation(btGoodsInfo);
            }
        });
    }

    int[] colors = {Color.RED,Color.BLUE,Color.BLACK,Color.GREEN,Color.YELLOW};
    public void addAnimation(View view){

        int[] startLocation = new int[2];

        view.getLocationInWindow(startLocation);

        PointF startF = new PointF();

        startF.x = startLocation[0];
        startF.y = startLocation[1];

        final TextView textView = new TextView(this);
        rlGoodsinfo.addView(textView);

        textView.setText("+1");
        textView.setTextColor(colors[(int) Math.floor(Math.random()*5)]);
        textView.setTextSize(17);

        textView.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.dpsize_30);
        textView.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.dpsize_30);
        textView.setVisibility(View.VISIBLE);
        int x = (int)(startF.x+Math.floor(Math.random()*300));

        textView.setX(x);
        textView.setY(startF.y-150f);

        Log.e("####",x+"/"+startF.y);

        ObjectAnimator objectAnimator = new ObjectAnimator().ofFloat(textView, "translationY", startF.y-150f,startF.y-350f);
        objectAnimator.setDuration(2000);

        ObjectAnimator objectAnimator2 = new ObjectAnimator().ofFloat(textView, "Alpha", 1,0);
        objectAnimator2.setDuration(2000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimator).with(objectAnimator2);
        animatorSet.setDuration(2000);
        animatorSet.start();

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                rlGoodsinfo.removeView(textView);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

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
        bbGoodsInfo.setCheckedItem(0);
        boolean loginStatus = UserManager.getInstance().getLoginStatus();
        if(loginStatus){
            List<Map<String, String>> collections = ShoppingManager.getInstance().getCollections(this);
            flag = 0;
            for (int i = 0; i < collections.size(); i++) {
                Map<String, String> map = collections.get(i);
                if(map.get("id").equals(goods_bean.getProduct_id())){
                    flag = 1;
                    collections.remove(i);
                    i--;
                    Drawable mine = getResources().getDrawable(R.drawable.mine);
                    Drawable collect = getResources().getDrawable(R.drawable.collect);
                    Drawable shoppingcart = getResources().getDrawable(R.drawable.shoppingcart);
                    Drawable[] drawables = new Drawable[]{mine, collect, shoppingcart};
                    bbGoodsInfo.setTapDrables(drawables);
                    break;
                }
            }
            if(flag==0){
                Map<String,String> map = new HashMap();
                map.put("id",goods_bean.getProduct_id());
                map.put("title",goods_bean.getName());
                map.put("price",goods_bean.getCover_price());
                map.put("img",goods_bean.getFigure());
                map.put("num",goods_bean.getNumber()+"");
                collections.add(map);
                Drawable mine = getResources().getDrawable(R.drawable.mine);
                Drawable collect = getResources().getDrawable(R.drawable.collectred);
                Drawable shoppingcart = getResources().getDrawable(R.drawable.shoppingcart);
                Drawable[] drawables = new Drawable[]{mine, collect, shoppingcart};
                bbGoodsInfo.setTapDrables(drawables);
            }
            Log.e("####collectionsize",collections.size()+"");
            ShoppingManager.getInstance().setCollections(this,collections);
        }else {
            setAlertDialog();
        }
    }

    private void setAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示：");
        builder.setMessage("您还没有登录");
        builder.setPositiveButton("前往登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(GoodsInfoActivity.this,LoginActivity.class));
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ShoppingManager.getInstance().setMainitem(0);
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //联系客服
    private void mine() {

    }

    @Override
    public void onPostDataSucess(InsertBean data) {

    }

    @Override
    public void onPostDataFailed(String ErrorMsg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        goodsInfoAdapter.getWebView();

        if (addOneProduct != null){
            addOneProduct.detachView();
        }

    }
}

